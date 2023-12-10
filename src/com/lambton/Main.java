package com.lambton;

import com.lambton.common.AppConstant;
import com.lambton.model.user.Manager;
import com.lambton.utility.AccountUtility;
import com.lambton.utility.FileUtilityImpl;

import static com.lambton.ProjectInput.projectMenu;

public class Main {

    static void initFile() {
        FileUtilityImpl<Manager> managerFileUtility = new FileUtilityImpl<>(AppConstant.USER_STORE_FILE, AppConstant.PROJECT_PREFIX);
        managerFileUtility.initFiles();
    }

    static void mainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1.👲🏻 Users");
        System.out.println("2.📊 Projects");
        System.out.println("3.🐞 Issues");
        int choice = ProjectInput.getInt("Enter your choice:");
        if (choice == 1) {
            UserInput.userMenu();
        } else if (choice == 2) {
            projectMenu();
        } else if (choice == 3) {
            IssueInput.issueMenu();
        }
    }

    public static void main(String[] args) {
        initFile();
        while (true) {
            try {
                if (AccountUtility.loggedInUser == null) {
                    System.out.println("User not logged in !!!");
                    String username = ProjectInput.getString("Username:");
                    String password = ProjectInput.getString("Password:");
                    AccountUtility.login(username, password);
                }
                mainMenu();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
