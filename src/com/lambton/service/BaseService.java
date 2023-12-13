package com.lambton.service;

import com.lambton.common.model.BaseModel;
import com.lambton.common.store.Store;
import com.lambton.enums.issue.IssueType;
import com.lambton.enums.user.UserType;
import com.lambton.model.issue.Issue;
import com.lambton.utility.AccountUtility;

public abstract class BaseService<T extends BaseModel, S extends Store<T>> {
    protected final S store;

    protected BaseService(S store) {
        this.store = store;
    }

    public T create(T entity) {
        if (null == AccountUtility.loggedInUser || AccountUtility.loggedInUser.getUserType() != UserType.MANAGER) {
            System.out.println("User not authorized!!!");
            return null;
        }
        T createdEntity = store.createEntity(entity);
        if (null != createdEntity) System.out.println("Created successfully!!!");
        return createdEntity;
    }

    public void update(String entityId, T entity) {
        if (null == AccountUtility.loggedInUser || AccountUtility.loggedInUser.getUserType() != UserType.MANAGER) {
            System.out.println("User not authorized!!!");
            return;
        }
        store.updateEntity(entityId, entity);
    }

    public void remove(String entityId) {
        if (null == AccountUtility.loggedInUser || AccountUtility.loggedInUser.getUserType() != UserType.MANAGER) {
            System.out.println("User not authorized !!!");
            return;
        }
        store.deleteEntity(entityId);
    }
}
