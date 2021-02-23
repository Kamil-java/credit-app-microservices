package pl.bak.credit.domain;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.bak.credit.domain.dao.CreditRepository;
import pl.bak.credit.domain.uri.UrlData;
import pl.bak.credit.dto.CreditDto;
import pl.bak.credit.dto.CustomerDto;
import pl.bak.credit.dto.MainDto;
import pl.bak.credit.dto.ProductDto;
import pl.bak.credit.model.Credit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CreditService {
    private final CreditRepository creditRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    public CreditService(CreditRepository creditRepository, ModelMapper modelMapper, RestTemplate restTemplate) {
        this.creditRepository = creditRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    public Integer createCredit(MainDto mainDto){

        CreditDto creditDto = mainDto.getCreditDto();
        Credit credit = creditRepository.save(modelMapper.map(creditDto, Credit.class));

        String productURL = UrlData.getProductCreateURL();
        ProductDto productDto = mainDto.getProductDto();
        productDto.setCreditDto(new CreditDto());
        productDto.getCreditDto().setId(credit.getId());
        productDto.getCreditDto().setCreditName(credit.getCreditName());
        restTemplate.postForObject(productURL, productDto, String.class);

        String customerURL = UrlData.getCustomerCreateURL();
        CustomerDto customerDto = mainDto.getCustomerDto();
        customerDto.setCreditDto(new CreditDto());
        customerDto.getCreditDto().setId(credit.getId());
        customerDto.getCreditDto().setCreditName(credit.getCreditName());
        restTemplate.postForObject(customerURL, customerDto, String.class);

        return credit.getId();
    }

    public List<MainDto> getAll(){
        List<Credit> credits = creditRepository.findAll();

        String customerURL = UrlData.getCustomerGetURL();
        CustomerDto[] customerDtos = restTemplate.getForObject(customerURL, CustomerDto[].class);

        String productURL = UrlData.getProductGetURL();
        ProductDto[] productDtos = restTemplate.getForObject(productURL, ProductDto[].class);

        if (!(customerDtos == null || productDtos == null)) {
            List<CustomerDto> customers = Arrays.asList(customerDtos);
            List<ProductDto> products = Arrays.asList(productDtos);

            List<MainDto> mainDtos = new ArrayList<>();

            credits.forEach(credit -> {
                MainDto mainDto = new MainDto();

                mainDto.setCreditDto(modelMapper.map(credit, CreditDto.class));

                customers.forEach(mainDto::setCustomerDto);

                products.forEach(mainDto::setProductDto);

                mainDtos.add(mainDto);
            });


            return mainDtos;
        }
        return Collections.emptyList();
    }

}
