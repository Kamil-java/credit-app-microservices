package pl.bak.credit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class CreditDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @NotBlank(message = "credit must have name")
    private String creditName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return id == creditDto.id && Objects.equals(creditName, creditDto.creditName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creditName);
    }
}
