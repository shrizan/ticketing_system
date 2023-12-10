package com.lambton.model.user;

import com.lambton.enums.user.UserType;

public class QualityAssurance extends User {
    public QualityAssurance(String firstName, String lastName,String username,String password) {
        super(firstName, lastName, UserType.QA,username,password);
    }
}
