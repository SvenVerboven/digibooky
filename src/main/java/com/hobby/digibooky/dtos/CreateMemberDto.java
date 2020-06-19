package com.hobby.digibooky.dtos;

import com.hobby.digibooky.domain.Address;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CreateMemberDto {

    @NotEmpty(message = "Inns number must not be empty")
    private String inns;
    private String firstName;
    @NotEmpty(message = "Last name must not be empty")
    private String lastName;
    @NotEmpty(message = "Email must not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Email is not valid")
    private String email;
    @Valid
    private Address address;

    public CreateMemberDto(String inns, String firstName, String lastName, String email, Address address) {
        this.inns = inns;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public String getInns() {
        return inns;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public void setInns(String inns) {
        this.inns = inns;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
