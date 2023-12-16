package com.lambton;

import com.lambton.common.AppConstant;
import com.lambton.common.model.BaseModel;
import com.lambton.common.util.AppUtil;
import com.lambton.enums.user.UserType;
import com.lambton.model.user.Dev;
import com.lambton.model.user.Manager;
import com.lambton.model.user.QualityAssurance;
import com.lambton.model.user.User;
import com.lambton.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserInput extends InputUtility {
    public static final UserService userService = new UserService();

    private static void printHeader() {
        System.out.println(AppUtil.formatString(30, "SN", "Full Name", "Username", "User Type"));
    }

    static UserType getUserType(UserType... userTypes) {
        UserType userType;
        int choice = 0;
        while (!(choice == 1 || choice == 2 || choice == 3)) {
            choice = getInt("Select User Type: 1. Manager 2. QA 3. Dev\nSelect Option:");
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
        String username;
        while (true) {
            username = getString("Username:");
            if (userService.getUserByUsername(username).isPresent()) {
                System.out.println("Username already exist!!!");
            } else {
                break;
            }
        }
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

    static void removeUser(User user) {
        userService.remove(user.getId());
    }

    static void updateUser(User user) {
        System.out.println("Old user type is " + user.getUserType());
        UserType userType = getUserType(user.getUserType());
        String firstName = getString(String.format("First name(%s):", user.getFirstName()));
        String lastName = getString(String.format("Last name(%s):", user.getLastName()));
        String username = getString(String.format("Username(%s):", user.getUsername()));
        user.setUserType(userType);
        user.setFirstName(isBlank(firstName) ? user.getFirstName() : firstName);
        user.setLastName(isBlank(lastName) ? user.getLastName() : lastName);
        user.setUsername(isBlank(username) ? user.getUsername() : username);
        userService.update(user.getId(), user);
    }

    static void displayUserList(List<User> users) {
        System.out.println();
        printHeader();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.printf("%s%s%n", getSN(Integer.toString(i + 1), 30), user);
        }
    }

    static User selectUser(List<User> users) {
        while (true) {
            int choice = getInt("Select from SN:");
            if (choice < 1 || choice > users.size()) {
                System.out.println("Invalid selection!!!");
            } else {
                return users.get(choice - 1);
            }
        }
    }

    static void displayDetails(User user) {
        List<Integer> choices = List.of(1, 2, 3);
        while (true) {
            System.out.println();
            printHeader();
            System.out.printf("%s%s%n", getSN(Integer.toString(1), 30), user);
            System.out.println();
            int choice = getInt("1.Update \t2.Remove \t3.Back\nSelect Option:");
            if (choices.contains(choice)) {
                if (choice == 1) {
                    updateUser(user);
                } else if (choice == 2) {
                    removeUser(user);
                }
                return;
            }
        }
    }

    static User fixedUserList(List<User> users) {
        while (true) {
            displayUserList(users);

            int choice = getInt("1.Remove \t2.Back\nSelect Option:");
            if (choice == 1) {
                return selectUser(users);
            } else if (choice == 2) {
                return null;
            }
        }
    }

    static User displayUserForSelect(List<User> users) {
        while (true) {
            displayUserList(users);

            int choice = getInt("1.Choose\t2.Filter\t3.Back\nSelect Option:");
            if (choice == 1) {
                return selectUser(users);

            } else if (choice == 2) {
                searchUser();
            } else if (choice == 3) {
                return null;
            }
        }
    }

    static void displayUser(List<User> users) {
        while (true) {
            displayUserList(users);

            int choice = getInt("1.Details\t2.Filter\t3.Back\nSelect Option:");
            if (choice == 1) {
                if (!users.isEmpty()) {
                    User user = selectUser(users);
                    displayDetails(user);
                } else {
                    System.out.println("No users to select!!!");
                }
            } else if (choice == 2) {
                searchUser();
            } else if (choice == 3) {
                return;
            }
        }
    }

    static void searchUser() {
        long page = getLong("Page number:");
        long size = getLong("Page size:");
        String firstName = getString("First name (Press enter for empty):");
        String lastName = getString("Last name (Press enter for empty):");
        Map<String, Optional<UserType>> userTypes = new HashMap<>();
        userTypes.put("1", Optional.of(UserType.MANAGER));
        userTypes.put("2", Optional.of(UserType.QA));
        userTypes.put("3", Optional.of(UserType.DEV));
        String userType = getString("User Type: 1. Manager 2.QA 3. Dev \n Select Option(or Press enter):");
        Optional<UserType> enteredUserType = userTypes.getOrDefault(userType, Optional.empty());

        List<User> users = userService.search(
                page,
                size,
                Optional.of(firstName),
                Optional.of(lastName),
                enteredUserType
        );

        displayUser(users);
    }

    static void viewUser() {
        List<User> users = userService.search(
                AppConstant.DEFAULT_PAGE,
                AppConstant.DEFAULT_SIZE,
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        displayUser(users);
    }

    static void userMenu() {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1.Create");
            System.out.println("2.View(Update and Remove)");
            System.out.println("3.Main menu");
            int choice = ProjectInput.getInt("Enter your choice:");
            if (choice == 1) {
                userService.create(getUserInput());
            } else if (choice == 2) {
                viewUser();
            } else if (choice == 3) {
                return;
            }
        }
    }
}
