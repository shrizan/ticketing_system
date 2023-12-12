package com.lambton.common.store;

import com.lambton.common.model.*;
import com.lambton.exception.*;
import com.lambton.utility.*;

import java.util.*;

public abstract class StoreImpl<T extends BaseModel> implements Store<T> {
    protected final FileUtility<T> fileUtility;

    public StoreImpl(FileUtility<T> fileUtility) {
        this.fileUtility = fileUtility;
    }

    @Override
    public T getEntity(String entityId) {
        Map<String, T> entities = fileUtility.readAllEntities();
        return entities.values().stream().filter(item -> item.getId().equals(entityId))
                .findFirst().orElse(null);
    }

    @Override
    public T createEntity(T entity) {
        Map<String, T> entities = fileUtility.readAllEntities();
        var oldEntity = getEntity(entity.getId());
        if (oldEntity != null) throw new EntityExistException();
        entities.put(entity.getId(), entity);
        fileUtility.writeToFile(entities);
        return entity;
    }

    @Override
    public T updateEntity(String entityId, T entity) {
        var oldEntity = getEntity(entityId);
        if (null == oldEntity) throw new EntityNotFoundException();
        var allEntities = fileUtility.readAllEntities();
        allEntities.remove(entityId);
        allEntities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public T deleteEntity(String entityId) {
        var oldEntity = getEntity(entityId);
        if (null == oldEntity) return null;
        var allEntities = fileUtility.readAllEntities();
        allEntities.remove(entityId);
        fileUtility.writeToFile(allEntities);
        return oldEntity;
    }
}
