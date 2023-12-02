package com.lambton.model.user;

import com.lambton.enums.user.UserType;

import java.util.UUID;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private transient String fullName;
    private UserType userType;

    public User(String firstName, String lastName, UserType userType) {
        this.id= UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
