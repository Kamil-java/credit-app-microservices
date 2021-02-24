package pl.bak.product.dto;

import java.util.Objects;

public class CreditDto {
    private Integer id;
    private String creditName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditDto creditDto = (CreditDto) o;
        return Objects.equals(id, creditDto.id) && Objects.equals(creditName, creditDto.creditName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creditName);
    }
}
