package com.lambton.store.user;

import com.lambton.enums.user.UserType;
import com.lambton.model.user.*;
import com.lambton.utility.FileUtility;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class QualityAssuranceUserStore extends BaseUserStoreImpl<QualityAssurance> {

    public QualityAssuranceUserStore(FileUtility<QualityAssurance> fileUtility) {
        super(fileUtility);
    }

    @Override
    public List<User> search(int page, int size, Optional<String> firstName, Optional<String> lastName) {
        Map<String, QualityAssurance> users = fileUtility.readAllEntities();
        return users
                .values()
                .stream()
                .skip(page * size)
                .limit(size)
                .filter(filterUserByUserType())
                .filter(filterUserByFirstName(firstName))
                .filter(filterUserByLastName(lastName))
                .collect(Collectors.toList());
    }

    private static Predicate<User> filterUserByUserType() {
        return user -> user.getUserType().equals(UserType.QA);
    }

    private static Predicate<User> filterUserByLastName(Optional<String> lastName) {
        return user -> lastName.map(name -> user.getLastName().toLowerCase().contains(name.toLowerCase())).orElse(true);
    }

    private static Predicate<User> filterUserByFirstName(Optional<String> firstName) {
        return user -> firstName.map(name -> user.getFirstName().toLowerCase().contains(name.toLowerCase())).orElse(true);
    }
}
