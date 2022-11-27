package com.periodicals.entity;

import com.periodicals.entity.enums.UserRole;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String localeId;
    private String firstname;
    private String lastname;
    private final String password;
    private final String email;
    private final UserRole userRole;
    private int balance;
    private boolean isBlocked;

    public User(final String localeId, final String firstname, final String lastname,
                final String password, final String email, final UserRole userRole, final int balance,
                final boolean isBlocked) {
        this.localeId = localeId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
        this.balance = balance;
        this.isBlocked = isBlocked;
    }

    public User(final int id, final String localeId, final String firstname, final String lastname,
                final String password, final String email,
                final UserRole userRole, final int balance, final boolean isBlocked) {
        this.id = id;
        this.localeId = localeId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
        this.balance = balance;
        this.isBlocked = isBlocked;
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

    public UserRole getRole() {
        return userRole;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(final boolean blocked) {
        isBlocked = blocked;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public void setBalance(int newBalance) {
        balance = newBalance;
    }

    public void setLocaleId(final String localeId) {
        this.localeId = localeId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return id == user.id && balance == user.balance && isBlocked == user.isBlocked
                && localeId.equals(user.localeId) && firstname.equals(user.firstname)
                && lastname.equals(user.lastname) && password.equals(user.password)
                && email.equals(user.email) && userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localeId, firstname, lastname, password, email, userRole, balance, isBlocked);
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
                ", role=" + userRole +
                ", balance=" + balance +
                ", isBlocked=" + isBlocked +
                '}';
    }
}