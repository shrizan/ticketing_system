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

    static FileUtility<Manager> managerFileUtility = new FileUtilityImpl<>(AppConstant.MANAGER_USER_STORE_FILE, PROJECT_PREFIX);
    static FileUtility<QualityAssurance> qualityAssuranceFileUtility = new FileUtilityImpl<>(AppConstant.QA_USER_STORE_FILE, PROJECT_PREFIX);
    static FileUtility<Dev> devFileUtility = new FileUtilityImpl<>(AppConstant.DEV_USER_STORE_FILE, PROJECT_PREFIX);

    public static boolean login(String username, String password) {
        Map<String, Manager> managers = managerFileUtility.readAllEntities();
        Map<String, QualityAssurance> qas = qualityAssuranceFileUtility.readAllEntities();
        Map<String, Dev> devs = devFileUtility.readAllEntities();
        var managerExistingUser = managers.values().stream().filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findAny();
        boolean isManager =
                managerExistingUser.map(user -> {
                    loggedInUser = user;
                    return true;
                }).orElse(false);

        var qaExistingUser = qas.values().stream().filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findAny();
        boolean isQA =
                qaExistingUser.map(user -> {
                    loggedInUser = user;
                    return true;
                }).orElse(false);
        var devExistingUser = devs.values().stream().filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findAny();
        boolean isDev =
                devExistingUser.map(user -> {
                    loggedInUser = user;
                    return true;
                }).orElse(false);
        if (isManager || isDev || isQA) {
            System.out.println("User authorized!!!");
        } else {
            System.out.println("User not authorized!!!");
        }
        return isManager || isQA || isDev;
    }

    public static boolean logOut() {
        loggedInUser = null;
        return true;
    }
}
