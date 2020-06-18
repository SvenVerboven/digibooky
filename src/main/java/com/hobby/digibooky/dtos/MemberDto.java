package com.hobby.digibooky.dtos;

import com.hobby.digibooky.domain.Address;

public class MemberDto {

    private Long id;
    private String inns;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;


    public MemberDto(Long id, String inns, String firstName, String lastName, String email, Address address) {
        this.id = id;
        this.inns = inns;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public Long getId() {
        return id;
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
}
