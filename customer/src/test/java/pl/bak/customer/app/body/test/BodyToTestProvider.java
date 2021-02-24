package pl.bak.customer.app.body.test;

import org.springframework.stereotype.Component;
import pl.bak.customer.dto.CreditDto;
import pl.bak.customer.dto.CustomerDto;
import pl.bak.customer.model.Credit;
import pl.bak.customer.model.Customer;

@Component
public class BodyToTestProvider {

    public Customer prepareCustomer(){
        Customer customer = new Customer();

        customer.setId(1);
        customer.setFirstName("Customer-1");
        customer.setSurname("C-Surname-1");
        customer.setPesel("12345678910");

        return customer;
    }

    public CustomerDto prepareCustomerDto(){
        CustomerDto customerDto = new CustomerDto();

        customerDto.setFirstName("Customer-1");
        customerDto.setSurname("C-Surname-1");
        customerDto.setPesel("12345678910");
        customerDto.setCreditDto(prepareCreditDto());

        return customerDto;
    }

    public Credit prepareCredit() {
        Credit credit = new Credit();

        credit.setId(1);
        credit.setCreditName("Credit-1");

        return credit;
    }

    public CreditDto prepareCreditDto() {
        CreditDto creditDto = new CreditDto();

        creditDto.setId(1);
        creditDto.setCreditName("Credit-1");

        return creditDto;
    }

}
