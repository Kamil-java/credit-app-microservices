package pl.bak.credit.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import pl.bak.credit.app.body.test.BodyToTestProvider;
import pl.bak.credit.domain.dao.CreditRepository;
import pl.bak.credit.dto.MainDto;
import pl.bak.credit.error.handler.ErrorHandler;
import pl.bak.credit.model.Credit;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

//    @Mock
//    private CreditRepository creditRepository;
//
//    private final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
//
//    private BodyToTestProvider bodyToTestProvider;
//
//    private CreditService creditService;
//
//    @BeforeEach
//    void setUp() {
//        bodyToTestProvider = new BodyToTestProvider();
//
//        restTemplateBuilder
//                .errorHandler(new ErrorHandler())
//                .build();
//
//        ModelMapper modelMapper = new ModelMapper();
//        creditService = new CreditService(creditRepository, modelMapper, restTemplateBuilder);
//    }
//
//    @Test
//    void shouldCreateCreditAndSendBodyToCustomerAndProduct() {
//        //given
//        given(creditRepository.save(any(Credit.class))).willReturn(bodyToTestProvider.prepareCredit());
//
//        //when
//        Optional<Credit> credit = creditService.createCredit(bodyToTestProvider.prepareMainDto());
//
//        //then
//        assertThat(credit.isPresent()).isTrue();
//        assertThat(credit)
//                .isNotNull()
//                .get()
//                .hasNoNullFieldsOrProperties()
//                .hasSameClassAs(new Credit())
//                .hasFieldOrProperty("id")
//                .hasFieldOrProperty("creditName");
//    }
//
//    @Test
//    void shouldGetAllCreditsAndProductAndCustomerFromOtherServices() {
//        //given
//        given(creditRepository.findAll()).willReturn(List.of(bodyToTestProvider.prepareCredit()));
//
//        //when
//        List<MainDto> all = creditService.getAll();
//
//        //then
//        assertThat(all)
//                .hasSize(0)
//                .isEmpty();
//
//    }
}