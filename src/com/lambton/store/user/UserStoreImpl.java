package com.lambton.store.user;

import com.lambton.common.store.*;
import com.lambton.enums.user.UserType;
import com.lambton.model.user.User;
import com.lambton.utility.FileUtility;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class UserStoreImpl<T extends User> extends StoreImpl<T> implements UserStore<T> {
    public UserStoreImpl(FileUtility<T> fileUtility) {
        super(fileUtility);
    }

    @Override
    public Map<String, T> allEntities() {
        return fileUtility.readAllEntities();
    }

    @Override
    public Optional<T> findByUsername(String username) {
        return allEntities().values().stream().filter(user->user.getUsername().equals(username)).findAny();
    }

    @Override
    public List<User> search(
            long page,
            long size,
            Optional<String> optionalFirstName,
            Optional<String> optionalLastName,
            Optional<UserType> userType
    ) {
        return fileUtility
                .readAllEntities()
                .values()
                .stream()
                .skip(page * size)
                .limit(size)
                .filter(filterByFirstName(optionalFirstName))
                .filter(filterByLastName(optionalLastName))
                .collect(Collectors.toList());
    }

    private static <T extends User> Predicate<T> filterByLastName(Optional<String> optionalLastName) {
        return user -> optionalLastName.map(lastName -> user.getLastName().toLowerCase().contains(lastName.toLowerCase())).orElse(true);
    }

    private static <T extends User> Predicate<T> filterByFirstName(Optional<String> optionalFirstName) {
        return user -> optionalFirstName.map(firstName -> user.getFirstName().toLowerCase().contains(firstName.toLowerCase())).orElse(true);
    }

    private static <T extends User> Predicate<T> filterByUserType(Optional<UserType> optionalUserType) {
        return user -> optionalUserType.map(userType -> user.getUserType().equals(userType)).orElse(true);
    }
}