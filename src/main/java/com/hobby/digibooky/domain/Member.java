package com.hobby.digibooky.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String inns;
    private String firstName;
    private String lastName;
    private String email;
    @Embedded
    private Address address;

    public Member(){}

    public Member(String inns, String firstName, String lastName, String email, Address address) {
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

    public void setId(Long id) {
        this.id = id;
    }
}
