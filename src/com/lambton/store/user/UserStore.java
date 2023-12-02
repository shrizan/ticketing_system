package com.lambton.store.user;

import com.lambton.model.project.Project;
import com.lambton.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserStore {
    User createUser(User user);

    User updateUser(String userId, User user);

    User deleteUser(String userId);

    User getUserById(String userId);
    List<User> search(int page, int size, Optional<String> firstName,Optional<String> lastName);
}
