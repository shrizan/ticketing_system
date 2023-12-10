package com.lambton.service;

import com.lambton.common.AppConstant;
import com.lambton.enums.project.ProjectType;
import com.lambton.enums.user.UserType;
import com.lambton.model.user.Dev;
import com.lambton.model.user.Manager;
import com.lambton.model.user.QualityAssurance;
import com.lambton.model.user.User;
import com.lambton.store.project.EnhancementProjectStore;
import com.lambton.store.project.GeneralProjectStore;
import com.lambton.store.user.DevUserStore;
import com.lambton.store.user.ManagerUserStore;
import com.lambton.store.user.QualityAssuranceUserStore;
import com.lambton.store.user.UserStore;
import com.lambton.utility.AccountUtility;
import com.lambton.utility.FileUtilityImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserService {
    private final Map<UserType, UserStore> userStoreMap = new HashMap<>();

    public UserService() {
        userStoreMap.put(UserType.MANAGER, new ManagerUserStore(new FileUtilityImpl<>(AppConstant.MANAGER_USER_STORE_FILE, AppConstant.PROJECT_PREFIX)));
        userStoreMap.put(UserType.QA, new QualityAssuranceUserStore(new FileUtilityImpl<>(AppConstant.QA_USER_STORE_FILE, AppConstant.PROJECT_PREFIX)));
        userStoreMap.put(UserType.DEV, new DevUserStore(new FileUtilityImpl<>(AppConstant.QA_USER_STORE_FILE, AppConstant.PROJECT_PREFIX)));
    }

    public void createUser(User user) {
        if (userStoreMap.get(UserType.MANAGER).allEntities().values().size() > 0) {
            if (null != AccountUtility.loggedInUser && AccountUtility.loggedInUser.getUserType().equals(UserType.MANAGER)) {
                userStoreMap.get(user.getUserType()).createEntity(user);
                System.out.println("New user created!!!");
            } else {
                System.out.println("Unauthorized user!!!");
            }
        } else {
            if (user.getUserType() == UserType.MANAGER) {
                userStoreMap.get(user.getUserType()).createEntity(user);
                AccountUtility.loggedInUser = user;
                System.out.println("New user created!!!");
            } else {
                System.out.println("First create a manager to create other user!!!");
            }
        }
    }

    public List<User> search(int page, int size, Optional<String> firstName, Optional<String> lastName, UserType userType) {
        return userStoreMap.get(userType).search(page, size, firstName, lastName);
    }

    public void updateUser(UserType oldUserType, User user) {
        if (oldUserType != user.getUserType()) {
            userStoreMap.get(oldUserType).deleteEntity(user.getId());
        }
        userStoreMap.get(user.getUserType()).updateEntity(user.getId(), user);
        System.out.println("Updated!!!");
    }

    public void removeUser(User user) {
        userStoreMap.get(user.getUserType()).deleteEntity(user.getId());
    }
}
