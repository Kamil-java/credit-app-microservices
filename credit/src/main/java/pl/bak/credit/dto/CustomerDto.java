package pl.bak.credit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    @NotBlank(message = "customer must have a first name")
    private String firstName;

    @NotBlank(message = "customer must have a surname")
    private String surname;

    @NotBlank(message = "customer must have a pesel")
    //TODO
//    @PESEL(message = "pesel must be at least 11 digits long")
    private String pesel;

    private CreditDto creditDto = new CreditDto();

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
        return Objects.equals(firstName, that.firstName) && Objects.equals(surname, that.surname) && Objects.equals(pesel, that.pesel) && Objects.equals(creditDto, that.creditDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, surname, pesel, creditDto);
    }
}
