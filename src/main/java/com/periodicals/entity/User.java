package com.periodicals.entity;

import com.periodicals.entity.enums.Role;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String localeId;
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String email;
    private final Role role;
    private final int balance;
    private final boolean isActive;
    private Address address;

    public User(final String localeId, final String firstname, final String lastname,
                final String password, final String email, final Role role, final int balance,
                final boolean isActive) {
        this.localeId = localeId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.role = role;
        this.balance = balance;
        this.isActive = isActive;
    }

    public User(final int id, final String localeId, final String firstname, final String lastname,
                final String password, final String email,
                final Role role, final int balance, final boolean isActive) {
        this.id = id;
        this.localeId = localeId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.role = role;
        this.balance = balance;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getLocaleId() {
        return localeId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isActive() {
        return isActive;
    }

    public Address getAddress() {
        return address;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setLocaleId(final String localeId) {
        this.localeId = localeId;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return id == user.id && balance == user.balance && isActive == user.isActive
                && localeId.equals(user.localeId) && firstname.equals(user.firstname)
                && lastname.equals(user.lastname) && password.equals(user.password)
                && email.equals(user.email) && role == user.role && Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localeId, firstname, lastname, password, email, role, balance, isActive, address);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", localeId='" + localeId + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", balance=" + balance +
                ", isActive=" + isActive +
                ", address=" + address +
                '}';
    }
}
