package com.lambton.model.user;

import com.lambton.enums.user.UserType;

public class Dev extends User {
    public Dev(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, UserType.DEV, username, password);
    }
}
