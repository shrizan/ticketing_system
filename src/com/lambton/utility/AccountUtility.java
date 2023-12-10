package com.lambton.utility;

import com.lambton.common.AppConstant;
import com.lambton.model.user.Dev;
import com.lambton.model.user.Manager;
import com.lambton.model.user.QualityAssurance;
import com.lambton.model.user.User;

import java.util.Map;

import static com.lambton.common.AppConstant.PROJECT_PREFIX;

public class AccountUtility {

    public static User loggedInUser;

    static FileUtility<User> userFileUtility = new FileUtilityImpl<>(AppConstant.USER_STORE_FILE, PROJECT_PREFIX);

    public static boolean login(String username, String password) {
        Map<String, User> users = userFileUtility.readAllEntities();
        var managerExistingUser = users.values().stream().filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findAny();
        boolean userExist =
                managerExistingUser.map(user -> {
                    loggedInUser = user;
                    return true;
                }).orElse(false);

        if (userExist) {
            System.out.println("User authorized!!!");
        } else {
            System.out.println("User not authorized!!!");
        }
        return userExist;
    }

    public static boolean logOut() {
        loggedInUser = null;
        return true;
    }
}
