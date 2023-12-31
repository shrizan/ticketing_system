package com.lambton.store.user;

import com.lambton.common.store.*;
import com.lambton.enums.user.UserType;
import com.lambton.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserStore<T extends User> extends Store<T> {

    Optional<T> findByUsername(String username);

    List<User> search(long page, long size, Optional<String> firstName, Optional<String> lastName, Optional<UserType> userType);
}
