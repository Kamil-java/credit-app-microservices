package pl.bak.credit.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.bak.credit.app.body.test.BodyToTestProvider;
import pl.bak.credit.domain.dao.CreditRepository;
import pl.bak.credit.domain.uri.URLData;
import pl.bak.credit.dto.CustomerDto;
import pl.bak.credit.dto.MainDto;
import pl.bak.credit.dto.ProductDto;
import pl.bak.credit.model.Credit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private RestTemplate restTemplate;

    private BodyToTestProvider bodyToTestProvider;

    private CreditService creditService;

    @BeforeEach
    void setUp() {
        bodyToTestProvider = new BodyToTestProvider();

        ModelMapper modelMapper = new ModelMapper();
        creditService = new CreditService(creditRepository, modelMapper, restTemplate);
    }

    @Test
    void shouldCreateCreditAndSendBodyToCustomerAndProduct() {
        //given
        given(creditRepository.save(any(Credit.class))).willReturn(getCredit());

        //when
        when(restTemplate.postForObject(URLData.customerCreateURL, getCustomerDto(), String.class)).thenReturn(null);
        when(restTemplate.postForObject(URLData.productCreateURL, getProductDto(), String.class)).thenReturn(null);
        Optional<Credit> credit = creditService.createCredit(bodyToTestProvider.prepareMainDto());

        //then
        assertThat(credit.isPresent()).isTrue();
        assertThat(credit)
                .isNotNull()
                .get()
                .hasNoNullFieldsOrProperties()
                .hasSameClassAs(new Credit())
                .hasFieldOrProperty("id")
                .hasFieldOrProperty("creditName");
    }

    @Test
    void shouldGetAllCreditsAndProductAndCustomerFromOtherServices() {
        //given
        given(creditRepository.findAll()).willReturn(List.of(getCredit()));

        //when
        when(restTemplate.getForObject(getURL(URLData.customerGetURL), CustomerDto[].class)).thenReturn(arrayCustomer());
        when(restTemplate.getForObject(getURL(URLData.productGetURL), ProductDto[].class)).thenReturn(arrayProduct());
        List<MainDto> all = creditService.getAll();

        //then
        assertThat(all)
                .isNotEmpty()
                .hasSize(1)
                .hasSameClassAs(new ArrayList<MainDto>())
                .hasOnlyElementsOfType(MainDto.class);
        assertThat(all.get(0))
                .isNotNull()
                .hasFieldOrProperty("creditDto")
                .hasFieldOrProperty("customerDto")
                .hasFieldOrProperty("productDto");


    }

    private String getURL(String URL) {
        List<Integer> id = creditRepository.findAll()
                .stream()
                .map(Credit::getId)
                .collect(Collectors.toList());
        return UriComponentsBuilder
                .fromHttpUrl(URL)
                .queryParam("creditId", id)
                .toUriString();
    }

    private Credit getCredit() {
        return bodyToTestProvider.prepareCredit();
    }

    private CustomerDto[] arrayCustomer() {
        return new CustomerDto[]{
                getCustomerDto()
        };
    }

    private ProductDto[] arrayProduct(){
        return new ProductDto[]{
                getProductDto()
        };
    }

    private CustomerDto getCustomerDto() {
        return bodyToTestProvider.prepareCustomerDto();
    }

    private ProductDto getProductDto() {
        return bodyToTestProvider.prepareProductDto();
    }
}