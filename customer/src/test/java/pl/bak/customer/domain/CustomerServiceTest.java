package pl.bak.customer.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.bak.customer.app.body.test.BodyToTestProvider;
import pl.bak.customer.domain.dao.CreditRepository;
import pl.bak.customer.domain.dao.CustomerRepository;
import pl.bak.customer.dto.CustomerDto;
import pl.bak.customer.model.Customer;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CreditRepository creditRepository;

    private BodyToTestProvider bodyToTestProvider;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        bodyToTestProvider = new BodyToTestProvider();

        ModelMapper modelMapper = new ModelMapper();
        customerService = new CustomerService(customerRepository, creditRepository, modelMapper);
    }

    @Test
    void shouldSaveCustomerIfPeselDoesntExistAndTheOtherwiseShouldReturnOptionalEmpty() {
        //given
        String peselDoesntExist = "098765432101";
        String peselExist = "12345678910";
        given(customerRepository.findByPesel(peselDoesntExist)).willReturn(Optional.empty());
        given(customerRepository.findByPesel(peselExist)).willReturn(Optional.of(new Customer()));
        given(creditRepository.findById(1)).willReturn(Optional.of(bodyToTestProvider.prepareCredit()));
        given(customerRepository.save(any(Customer.class))).willReturn(bodyToTestProvider.prepareCustomer());
        doNothing().when(creditRepository).deleteById(1);

        //when
        CustomerDto customerDto = bodyToTestProvider.prepareCustomerDto();
        Optional<Customer> customerIncorrect = customerService.saveCustomer(customerDto);
        customerDto.setPesel(peselDoesntExist);
        Optional<Customer> customer = customerService.saveCustomer(customerDto);

        //then
        assertThat(customerIncorrect.isPresent()).isFalse();

        assertThat(customer.isPresent()).isTrue();
        assertThat(customer.get())
                .hasSameClassAs(bodyToTestProvider.prepareCustomer())
                .hasFieldOrProperty("id")
                .hasFieldOrProperty("firstName")
                .hasFieldOrProperty("surname")
                .hasFieldOrProperty("pesel");

        verify(creditRepository).deleteById(1);


    }
    @Test
    void shouldReturnListOfCustomer() {
        //given
        given(customerRepository.findAll()).willReturn(List.of(bodyToTestProvider.prepareCustomer()));

        //when
        List<CustomerDto> customersDto = customerService.getAll();

        //then
        assertThat(customersDto)
                .isNotEmpty()
                .hasSize(1);
        assertThat(customersDto.get(0))
                .isNotNull()
                .hasSameClassAs(bodyToTestProvider.prepareCustomerDto())
                .hasFieldOrProperty("firstName")
                .hasFieldOrProperty("surname")
                .hasFieldOrProperty("pesel");
    }
}