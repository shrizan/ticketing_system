package com.lambton.store.user;

import com.lambton.common.store.*;
import com.lambton.model.project.Project;
import com.lambton.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserStore<T extends User> extends Store<T> {
    List<User> search(int page, int size, Optional<String> firstName, Optional<String> lastName);
}
