package pl.bak.credit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private int id;

    @NotBlank(message = "product must have name")
    private String productName;

    @Positive(message = "value must be positive")
    private int value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private CreditDto creditDto = new CreditDto();

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
        return id == that.id && value == that.value && Objects.equals(productName, that.productName) && Objects.equals(creditDto, that.creditDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, value, creditDto);
    }
}
