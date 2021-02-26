package pl.bak.credit.app.body.test;

import org.springframework.stereotype.Component;
import pl.bak.credit.dto.CreditDto;
import pl.bak.credit.dto.CustomerDto;
import pl.bak.credit.dto.MainDto;
import pl.bak.credit.dto.ProductDto;
import pl.bak.credit.model.Credit;

@Component
public class BodyToTestProvider {

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

    public CustomerDto prepareCustomerDto(){
        CustomerDto customerDto = new CustomerDto();

        customerDto.setFirstName("Customer-1");
        customerDto.setSurname("C-Surname-1");
        customerDto.setPesel("55030101193");
        customerDto.setCreditDto(prepareCreditDto());

        return customerDto;
    }

    public ProductDto prepareProductDto() {
        ProductDto productDto = new ProductDto();

        productDto.setProductName("Product-1");
        productDto.setValue(200);
        productDto.setCreditDto(prepareCreditDto());

        return productDto;
    }

    public MainDto prepareMainDto(){
        MainDto mainDto = new MainDto();

        mainDto.setCustomerDto(prepareCustomerDto());
        mainDto.setCreditDto(prepareCreditDto());
        mainDto.setProductDto(prepareProductDto());

        return mainDto;
    }

}
