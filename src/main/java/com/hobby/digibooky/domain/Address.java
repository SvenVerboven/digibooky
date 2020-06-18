package com.hobby.digibooky.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Embeddable
public class Address {

    private String streetName;
    private String houseNumber;
    private String postalCode;
    private String city;

    public Address(String streetName, String houseNumber, String postalCode, String city) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Address(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(streetName, address.streetName) &&
                Objects.equals(houseNumber, address.houseNumber) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, houseNumber, postalCode, city);
    }
}
