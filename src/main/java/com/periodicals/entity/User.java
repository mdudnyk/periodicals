package com.periodicals.entity;

import com.periodicals.entity.enums.BlockingStatus;
import com.periodicals.entity.enums.Role;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private int localeId;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private Role role;
    private int balance;
    private BlockingStatus blockingStatus;
    private Address address;

    public User(final int id, final int localeId, final String firstname, final String lastname,
                final String password, final String email, final Role role, final int balance,
                final BlockingStatus blockingStatus) {
        this.id = id;
        this.localeId = localeId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.role = role;
        this.balance = balance;
        this.blockingStatus = blockingStatus;
    }


    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getLocaleId() {
        return localeId;
    }

    public void setLocaleId(final int localeId) {
        this.localeId = localeId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(final int balance) {
        this.balance = balance;
    }

    public BlockingStatus getBlockingStatus() {
        return blockingStatus;
    }

    public void setBlockingStatus(final BlockingStatus blockingStatus) {
        this.blockingStatus = blockingStatus;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return id == user.id && localeId == user.localeId && balance == user.balance
                && firstname.equals(user.firstname) && lastname.equals(user.lastname)
                && password.equals(user.password) && email.equals(user.email)
                && role == user.role && blockingStatus == user.blockingStatus
                && Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localeId, firstname, lastname, password, email, role, balance, blockingStatus, address);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", localeId=" + localeId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", balance=" + balance +
                ", blockingStatus=" + blockingStatus +
                ", address=" + address +
                '}';
    }
}
