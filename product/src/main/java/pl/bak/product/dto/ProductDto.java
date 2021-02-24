package pl.bak.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private int id;
    private String productName;
    private int value;
    private CreditDto creditDto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public CreditDto getCreditDto() {
        return creditDto;
    }

    public void setCreditDto(CreditDto creditDto) {
        this.creditDto = creditDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return value == that.value && Objects.equals(productName, that.productName) && Objects.equals(creditDto, that.creditDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, value, creditDto);
    }
}
