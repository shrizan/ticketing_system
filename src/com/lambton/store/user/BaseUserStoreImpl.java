package com.lambton.store.user;

import com.lambton.common.store.*;
import com.lambton.model.user.User;
import com.lambton.store.project.*;
import com.lambton.utility.FileUtility;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public class BaseUserStoreImpl<T extends User> extends StoreImpl<T> implements UserStore<T> {
    public BaseUserStoreImpl(FileUtility<T> fileUtility) {
        super(fileUtility);
    }

    @Override
    public Map<String, T> allEntities() {
        return fileUtility.readAllEntities();
    }

    @Override
    public List<User> search(int page, int size, Optional<String> firstName, Optional<String> lastName) {
        return null;
    }
}