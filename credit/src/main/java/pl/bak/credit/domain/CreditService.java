package pl.bak.credit.domain;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.bak.credit.domain.dao.CreditRepository;
import pl.bak.credit.domain.uri.URLData;
import pl.bak.credit.dto.CreditDto;
import pl.bak.credit.dto.CustomerDto;
import pl.bak.credit.dto.MainDto;
import pl.bak.credit.dto.ProductDto;
import pl.bak.credit.model.Credit;

import java.util.*;
import java.util.stream.Collectors;


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

        if (credits.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> id = credits.stream()
                .map(Credit::getId)
                .collect(Collectors.toList());

        String productURL = uriWithParam(URLData.productGetURL, id);

        ProductDto[] resultProducts = restTemplate.getForObject(productURL, ProductDto[].class);

        String customerURL = uriWithParam(URLData.customerGetURL, id);

        CustomerDto[] ResultCustomers = restTemplate.getForObject(customerURL, CustomerDto[].class);

        if (!(ResultCustomers == null || resultProducts == null)) {
            List<CustomerDto> customers = Arrays.asList(ResultCustomers);
            List<ProductDto> products = Arrays.asList(resultProducts);
            List<MainDto> mainDtos = new ArrayList<>();

            credits.forEach(credit -> mainDtos.add(extractedByIdAndMapToMainDto(customers, products, credit)));

            return mainDtos;
        }
        return Collections.emptyList();
    }

    private MainDto extractedByIdAndMapToMainDto(List<CustomerDto> customers,
                                                 List<ProductDto> products, Credit credit) {
        MainDto mainDto = new MainDto();
        Integer id = credit.getId();

        mainDto.setCreditDto(modelMapper.map(credit, CreditDto.class));

        customers
                .stream()
                .filter(customerDto -> customerDto.getId() == id)
                .forEach(customerDto -> {
                    customerDto.setCreditDto(null);
                    mainDto.setCustomerDto(customerDto);
                });

        products
                .stream()
                .filter(productDto -> productDto.getId() == id)
                .forEach(productDto -> {
                    productDto.setCreditDto(null);
                    mainDto.setProductDto(productDto);
                });

        return mainDto;
    }

    private String uriWithParam(String url, List<Integer> id) {
        return UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("creditId", id)
                .toUriString();
    }

}
