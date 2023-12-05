package com.lambton.common.store;

import com.lambton.common.model.*;

public interface Store<T extends BaseModel> {
    T createEntity(T entity);

    T updateEntity(String entityId, T entity);

    T deleteEntity(String entityId);

    T getEntity(String entityId);
}
