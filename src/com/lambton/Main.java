package com.lambton;

import com.lambton.common.AppConstant;
import com.lambton.enums.user.UserType;
import com.lambton.model.user.Manager;
import com.lambton.utility.AccountUtility;
import com.lambton.utility.FileUtilityImpl;

import java.util.List;

public class Main {

    static void initFile() {
        FileUtilityImpl<Manager> managerFileUtility = new FileUtilityImpl<>(AppConstant.USER_STORE_FILE, AppConstant.PROJECT_PREFIX);
        managerFileUtility.initFiles();
    }

    static void userMenu() {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1.Projects");
            System.out.println("2.Issues");
            System.out.println("3.log out");
            int choice = ProjectInput.getInt("Enter your choice:");
            if (choice == 1) {
                ProjectInput.userProjectMenu();
            } else if (choice == 2) {
                IssueInput.userMenu();
            } else if (choice == 3) {
                AccountUtility.logOut();
                return;
            }
        }
    }

    static void managerMenu() {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1.Users");
            System.out.println("2.Projects");
            System.out.println("3.Issues");
            System.out.println("4.Log out");
            int choice = ProjectInput.getInt("Enter your choice:");
            if (choice == 1) {
                UserInput.userMenu();
            } else if (choice == 2) {
                ProjectInput.managerProjectMenu();
            } else if (choice == 3) {
                IssueInput.managerMenu();
            } else if (choice == 4) {
                AccountUtility.logOut();
                return;
            }
        }
    }

    static void mainMenu() {
        List<Integer> choices = List.of(1, 2, 3);
        while (true) {
            System.out.println("1. Register User");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = InputUtility.getInt("Select an option:");
            if (choices.contains(choice)) {
                if (choice == 1) {
                    UserInput.createUser();
                } else if (choice == 2) {
                    if (UserInput.login()) {
                        System.out.printf("*****Welcome back (%s)*****\n", AccountUtility.loggedInUser.getFullName());
                        if (AccountUtility.loggedInUser.getUserType().equals(UserType.MANAGER)) {
                            managerMenu();
                        } else {
                            userMenu();
                        }
                    } else {
                        System.out.println("Invalid username or password");
                    }
                } else {
                    return;
                }
            } else {
                System.out.println("Invalid option!!!");
            }
        }

    }

    public static void main(String[] args) {
        initFile();
        mainMenu();
    }
}
