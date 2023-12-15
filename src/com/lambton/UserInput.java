package com.lambton;

import com.lambton.common.model.BaseModel;
import com.lambton.enums.user.UserType;
import com.lambton.model.project.Project;
import com.lambton.model.user.Dev;
import com.lambton.model.user.Manager;
import com.lambton.model.user.QualityAssurance;
import com.lambton.model.user.User;
import com.lambton.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserInput extends InputUtility {
    public static final UserService userService = new UserService();

    static void addUsersToSuggestionList(List<User> suggestedUser) {
        String firstName = getString("First name:");
        String lastName = getString("Last name:");
        UserType userType = getUserType();
        List<User> users = userService.search(0, 10, Optional.of(firstName), Optional.of(lastName), userType);
        List<User> updatedUsers = users.stream().filter(user -> !suggestedUser.stream().map(BaseModel::getId).collect(Collectors.toList()).contains(user.getId()))
                .collect(Collectors.toList());
        displayUserList(updatedUsers);
        int choice = -1;
        while (true) {
            choice = getInt("Select SN(Enter zero to done):");
            if (choice == 0) break;
            if (choice < 1 || choice > updatedUsers.size()) {
                System.out.println("Invalid selection");
            }
            suggestedUser.add(updatedUsers.get(choice - 1));
        }
    }

    static UserType getUserType(UserType... userTypes) {
        UserType userType = userTypes.length > 0 ? userTypes[0] : null;
        int choice = 0;
        while (!(choice == 1 || choice == 2 || choice == 3)) {
            choice = getInt("Select User Type: 1. Manager 2. QA 3. Dev");
            if (!List.of(1, 2, 3).contains(choice) && userTypes.length > 0) {
                break;
            }
        }
        switch (choice) {
            case 1:
                userType = UserType.MANAGER;
                break;
            case 2:
                userType = UserType.QA;
                break;
            default:
                userType = UserType.DEV;
                break;
        }
        return userType;
    }

    static User getUserInput() {
        UserType userType = getUserType();
        String firstName = getString("First name:");
        String lastName = getString("Last name:");
        String username = getString("Username:");
        String password = getString("Password:");
        if (userType == UserType.MANAGER) {
            return new Manager(firstName, lastName, username, password);
        } else if (userType == UserType.QA) {
            return new QualityAssurance(firstName, lastName, username, password);
        } else {
            return new Dev(firstName, lastName, username, password);
        }
    }

    static boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }

    static void removeUser(List<User> users) {
        int choice = getInt("Select from SN:");
        if (choice < 1 || choice > users.size()) {
            System.out.println("Invalid selection!!!");
            return;
        }
        User user = users.get(choice - 1);
        userService.removeUser(user);
        users.remove(user);
    }

    static void updateUser(List<User> users) {
        int choice = getInt("Select from SN:");
        if (choice < 1 || choice > users.size()) {
            System.out.println("Invalid selection!!!");
            return;
        }
        User user = users.get(choice - 1);
        System.out.println("Old user type is " + user.getUserType());
        UserType oldUserType = user.getUserType();
        UserType userType = getUserType(user.getUserType());
        String firstName = getString(String.format("First name(%s):", user.getFirstName()));
        String lastName = getString(String.format("Last name(%s):", user.getLastName()));
        String username = getString(String.format("Username(%s):", user.getUsername()));
        user.setUserType(userType);
        user.setFirstName(isBlank(firstName) ? user.getFirstName() : firstName);
        user.setLastName(isBlank(lastName) ? user.getLastName() : lastName);
        user.setUsername(isBlank(username) ? user.getUsername() : username);
        userService.updateUser(oldUserType, user);
    }

    static void displayUserList(List<User> users) {
        System.out.println("SN\tFirst Name\tLast Name\tUsername");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.printf("%d\t%s\t%s\t%s\n", i + 1, user.getFirstName(), user.getLastName(), user.getUsername());
        }
    }

    static void displayDetails(List<User> users) {
        int choice = getInt("Select from SN:");
        if (choice < 1 || choice > users.size()) {
            System.out.println("Invalid selection!!!");
            return;
        }
        User user = users.get(choice - 1);
        System.out.println(user);
    }

    static void displayUser(List<User> users) {
        while (true) {
            displayUserList(users);

            int choice = getInt("1.⬆️Update\t2.⛔Remove\t3.ℹ️Details\t4.⬅️Back\nSelect Option:");
            if (choice == 1) {
                updateUser(users);
            } else if (choice == 2) {
                removeUser(users);
            } else if (choice == 3) {
                displayDetails(users);
            } else if (choice == 4) {
                return;
            }
        }
    }

    static void viewUser() {
        int page = getInt("page:");
        int size = getInt("size:");
        String firstName = getString("First Name:");
        String lastName = getString("Last Name:");
        UserType userType = getUserType();
        List<User> users = userService.search(page, size, Optional.of(firstName), Optional.of(lastName), userType);
        displayUser(users);
    }

    static void userMenu() {
        while (true) {
            System.out.println("\nProject Menu:");
            System.out.println("1. Create");
            System.out.println("2. View(Update and Remove)");
            System.out.println("3. Main menu");
            int choice = ProjectInput.getInt("Enter your choice:");
            if (choice == 1) {
                userService.createUser(getUserInput());
            } else if (choice == 2) {
                viewUser();
            } else if (choice == 3) {
                return;
            }
        }
    }
}
