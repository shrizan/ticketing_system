package com.lambton.common.store;

import com.lambton.common.model.*;

import java.util.List;
import java.util.Map;

public interface Store<T extends BaseModel> {
    T createEntity(T entity);

    T updateEntity(String entityId, T entity);

    T deleteEntity(String entityId);

    T getEntity(String entityId);

    Map<String,T> allEntities();
}
