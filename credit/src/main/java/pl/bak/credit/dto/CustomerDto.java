package pl.bak.credit.dto;

import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.NotBlank;

public class CustomerDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String surname;

    @NotBlank
    @PESEL
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
}
