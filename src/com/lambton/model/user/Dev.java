package com.lambton.model.user;

import com.lambton.enums.user.UserType;

public class Dev extends User{
    public Dev(String firstName, String lastName) {
        super(firstName, lastName, UserType.DEV);
    }
}
