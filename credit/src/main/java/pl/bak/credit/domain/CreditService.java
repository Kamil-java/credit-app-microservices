package pl.bak.credit.domain;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.bak.credit.domain.dao.CreditRepository;
import pl.bak.credit.domain.uri.URLData;
import pl.bak.credit.dto.CreditDto;
import pl.bak.credit.dto.CustomerDto;
import pl.bak.credit.dto.MainDto;
import pl.bak.credit.dto.ProductDto;
import pl.bak.credit.model.Credit;

import java.util.*;


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


    public Optional<Credit> createCredit(MainDto mainDto) {

        CreditDto creditDto = mainDto.getCreditDto();
        Credit credit = creditRepository.save(modelMapper.map(creditDto, Credit.class));

        String customerURL = URLData.customerCreateURL;
        CustomerDto customerDto = mainDto.getCustomerDto();
        customerDto.setCreditDto(new CreditDto());
        modelMapper.map(credit, customerDto.getCreditDto());
        restTemplate.postForObject(customerURL, customerDto, String.class);

        String productURL = URLData.productCreateURL;
        ProductDto productDto = mainDto.getProductDto();
        productDto.setCreditDto(new CreditDto());
        modelMapper.map(credit, productDto.getCreditDto());
        restTemplate.postForObject(productURL, productDto, String.class);

        return Optional.of(credit);
    }

    public List<MainDto> getAll() {
        List<Credit> credits = creditRepository.findAll();

        String productURL = URLData.productGetURL;
        ProductDto[] productDtos = restTemplate.getForObject(productURL, ProductDto[].class);

        String customerURL = URLData.customerGetURL;
        CustomerDto[] customerDtos = restTemplate.getForObject(customerURL, CustomerDto[].class);

        if (!(customerDtos == null || productDtos == null)) {
            List<CustomerDto> customers = Arrays.asList(customerDtos);
            List<ProductDto> products = Arrays.asList(productDtos);

            List<MainDto> mainDtos = new ArrayList<>();

            credits.forEach(credit -> {
                MainDto mainDto = new MainDto();

                mainDto.setCreditDto(modelMapper.map(credit, CreditDto.class));

                customers.forEach(customerDto -> {
                    customerDto.setCreditDto(null);
                    mainDto.setCustomerDto(customerDto);
                });

                products.forEach(productDto -> {
                    productDto.setCreditDto(null);
                    mainDto.setProductDto(productDto);
                });

                mainDtos.add(mainDto);
            });


            return mainDtos;
        }
        return Collections.emptyList();
    }

}
