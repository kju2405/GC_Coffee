package com.example.gccoffee.model;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

public class Phone {
    private final String phoneNumber;

    public Phone(String phoneNumber) {
        Assert.notNull(phoneNumber, "phone number should not be null.");
        Assert.isTrue(checkPhoneNumber(phoneNumber), "Invalid phone number.");
        this.phoneNumber = phoneNumber;
    }

    private static boolean checkPhoneNumber(String phoneNumber) {
        return Pattern.matches("^010([0-9]{3,4})([0-9]{4})$", phoneNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(phoneNumber, phone.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return phoneNumber;
    }
}
