package com.lambton.model.user;

import com.lambton.common.model.*;
import com.lambton.enums.user.UserType;

import java.util.UUID;

public class User extends BaseModel {
    private String firstName;
    private String lastName;
    private transient String fullName;
    private UserType userType;
    private String username;
    private String password;

    public User(String firstName, String lastName, UserType userType, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
