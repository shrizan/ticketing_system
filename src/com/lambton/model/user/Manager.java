package com.lambton.model.user;

import com.lambton.enums.user.UserType;

public class Manager extends User {
    public Manager(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, UserType.MANAGER, username, password);
    }


}
