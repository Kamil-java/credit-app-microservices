package pl.bak.credit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.util.Objects;

public class MainDto {
    @JsonProperty("Credit")
    @Valid
    private CreditDto creditDto;

    @JsonProperty("Customer")
    @Valid
    private CustomerDto customerDto;

    @JsonProperty("Product")
    @Valid
    private ProductDto productDto;

    public CreditDto getCreditDto() {
        return creditDto;
    }

    public void setCreditDto(CreditDto creditDto) {
        this.creditDto = creditDto;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainDto mainDto = (MainDto) o;
        return Objects.equals(creditDto, mainDto.creditDto) && Objects.equals(customerDto, mainDto.customerDto) && Objects.equals(productDto, mainDto.productDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditDto, customerDto, productDto);
    }
}
