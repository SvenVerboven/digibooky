package com.hobby.digibooky.dtos;

import com.hobby.digibooky.domain.Address;

public class CreateMemberDto {

    private String inns;
    private String firstName;
    private String lastName;
    private String email;
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
