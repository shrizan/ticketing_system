package com.lambton.model.user;

import com.lambton.common.model.*;
import com.lambton.common.util.AppUtil;
import com.lambton.enums.user.UserType;

import java.util.List;
import java.util.UUID;

public class User extends BaseModel {
    private static final long serialVersionUID = 2591121789721221043L;
    private String firstName;
    private String lastName;
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

    @Override
    public String toString() {
        return AppUtil.formatString(
                30,
                getFullName(),
                username,
                userType.toString()
        );
    }
}
