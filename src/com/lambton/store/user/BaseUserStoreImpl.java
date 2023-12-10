package com.lambton.store.user;

import com.lambton.common.store.*;
import com.lambton.model.user.User;
import com.lambton.store.project.*;
import com.lambton.utility.FileUtility;

import java.util.Map;


public abstract class BaseUserStoreImpl<T extends User> extends StoreImpl<T> implements UserStore<T> {
    public BaseUserStoreImpl(FileUtility<T> fileUtility) {
        super(fileUtility);
    }

    @Override
    public Map<String, T> allEntities() {
        return fileUtility.readAllEntities();
    }
}