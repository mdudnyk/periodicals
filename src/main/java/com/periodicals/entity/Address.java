package com.periodicals.entity;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {
    private int index;
    private String city;
    private String street;
    private String build;
    private String apartment;

    public Address(final int index, final String city, final String street, final String build, final String apartment) {
        this.index = index;
        this.city = city;
        this.street = street;
        this.build = build;
        this.apartment = apartment;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(final String build) {
        this.build = build;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(final String apartment) {
        this.apartment = apartment;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Address address = (Address) o;
        return index == address.index && city.equals(address.city) && street.equals(address.street)
                && build.equals(address.build) && apartment.equals(address.apartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, city, street, build, apartment);
    }

    @Override
    public String toString() {
        return "Address{" +
                "index=" + index +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", build='" + build + '\'' +
                ", apartment='" + apartment + '\'' +
                '}';
    }
}
