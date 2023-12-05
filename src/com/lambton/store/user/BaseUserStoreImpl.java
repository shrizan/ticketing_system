package com.lambton.store.user;

import com.lambton.common.store.*;
import com.lambton.model.user.User;
import com.lambton.store.project.*;
import com.lambton.utility.FileUtility;


public abstract class BaseUserStoreImpl<T extends User> extends StoreImpl<T> implements UserStore<T> {
    public BaseUserStoreImpl(FileUtility<T> fileUtility) {
        super(fileUtility);
    }
}