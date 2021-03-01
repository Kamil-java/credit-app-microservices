package pl.bak.customer.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.pl.PESEL;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    private int id;
    private String firstName;
    private String surname;

    @PESEL
    private String pesel;
    private CreditDto creditDto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
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
        CustomerDto that = (CustomerDto) o;
        return id == that.id && Objects.equals(firstName, that.firstName) && Objects.equals(surname, that.surname) && Objects.equals(pesel, that.pesel) && Objects.equals(creditDto, that.creditDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, surname, pesel, creditDto);
    }
}
