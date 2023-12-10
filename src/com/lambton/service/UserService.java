package com.lambton.service;

import com.lambton.common.AppConstant;
import com.lambton.enums.user.UserType;
import com.lambton.model.user.User;
import com.lambton.store.user.BaseUserStoreImpl;
import com.lambton.store.user.UserStore;
import com.lambton.utility.AccountUtility;
import com.lambton.utility.FileUtilityImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserService {
    private final UserStore<User> userStore = new BaseUserStoreImpl<>(new FileUtilityImpl<>(AppConstant.USER_STORE_FILE, AppConstant.PROJECT_PREFIX));


    public void createUser(User user) {
        if (!userStore.allEntities().values().isEmpty()) {
            if (null != AccountUtility.loggedInUser && AccountUtility.loggedInUser.getUserType().equals(UserType.MANAGER)) {
                userStore.createEntity(user);
                System.out.println("New user created!!!");
            } else {
                System.out.println("Unauthorized user!!!");
            }
        } else {
            if (user.getUserType() == UserType.MANAGER) {
                userStore.createEntity(user);
                AccountUtility.loggedInUser = user;
                System.out.println("New user created!!!");
            } else {
                System.out.println("First create a manager to create other user!!!");
            }
        }
    }

    public List<User> search(int page, int size, Optional<String> firstName, Optional<String> lastName, UserType userType) {
        return userStore.search(page, size, firstName, lastName);
    }

    public void updateUser(UserType oldUserType, User user) {
        if (oldUserType != user.getUserType()) {
            userStore.deleteEntity(user.getId());
        }
        userStore.updateEntity(user.getId(), user);
        System.out.println("Updated!!!");
    }

    public void removeUser(User user) {
        userStore.deleteEntity(user.getId());
    }
}