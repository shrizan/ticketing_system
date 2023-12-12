package com.lambton.service;

import com.lambton.common.AppConstant;
import com.lambton.enums.user.UserType;
import com.lambton.model.project.Project;
import com.lambton.model.user.User;
import com.lambton.store.project.ProjectStore;
import com.lambton.store.user.UserStoreImpl;
import com.lambton.store.user.UserStore;
import com.lambton.utility.AccountUtility;
import com.lambton.utility.FileUtilityImpl;

import java.util.List;
import java.util.Optional;

public class UserService extends BaseService<User, UserStore<User>> {


    public UserService() {
        super(new UserStoreImpl<>(new FileUtilityImpl<>(AppConstant.USER_STORE_FILE, AppConstant.PROJECT_PREFIX)));
    }

    @Override
    public User create(User user) {
        if (!store.allEntities().values().isEmpty()) {
            if (null != AccountUtility.loggedInUser && AccountUtility.loggedInUser.getUserType().equals(UserType.MANAGER)) {
                User createdUser = store.createEntity(user);
                if (null != createdUser) System.out.println("New user created!!!");
                else System.out.println("Something went wrong while creating the user!!!");
                return createdUser;
            } else {
                System.out.println("Unauthorized user!!!");
            }
        } else {
            if (user.getUserType() == UserType.MANAGER) {
                User createdUser = store.createEntity(user);
                AccountUtility.loggedInUser = user;
                if (null != createdUser) System.out.println("New user created!!!");
                else System.out.println("Something went wrong while creating the user!!!");
            } else {
                System.out.println("First create a manager to create other user!!!");
            }
        }
        return null;
    }

    public List<User> search(long page, long size, Optional<String> firstName, Optional<String> lastName, Optional<UserType> userType) {
        return store.search(page, size, firstName, lastName, userType);
    }
}
