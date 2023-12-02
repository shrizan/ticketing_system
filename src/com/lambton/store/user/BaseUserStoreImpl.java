package com.lambton.store.user;

import com.lambton.exception.EntityCreationException;
import com.lambton.exception.EntityExistException;
import com.lambton.exception.EntityNotFoundException;
import com.lambton.model.user.User;
import com.lambton.utility.FileUtility;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

abstract public class BaseUserStoreImpl implements UserStore {
    private final FileUtility fileUtility;

    public BaseUserStoreImpl(FileUtility fileUtility) {
        this.fileUtility= fileUtility;
    }

    protected Map<String, User> readAllEntities() {
        try (
                FileInputStream fileInputStream = new FileInputStream(fileUtility.getFilePath());
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)
        ) {
            return (Map<String, User>) inputStream.readObject();
        } catch (EOFException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    protected boolean writeToFile(Map<String, User> entities) {
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileUtility.getFilePath()))
        ) {
            outputStream.writeObject(entities);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User createUser(User user) {
        Map<String, User> users = readAllEntities();
        if (users.containsKey(user.getId())) {
            throw new EntityExistException();
        }
        users.put(user.getId(), user);
        boolean isSuccess = writeToFile(users);
        if (isSuccess) return user;
        else throw new EntityCreationException();
    }

    @Override
    public User updateUser(String userId, User user) {
        Map<String, User> users = readAllEntities();
        if (!users.containsKey(userId)) throw new EntityNotFoundException();
        users.put(userId, user);
        boolean isSuccess = writeToFile(users);
        if (isSuccess) return user;
        else throw new EntityCreationException();
    }

    @Override
    public User deleteUser(String userId) {
        Map<String, User> users = readAllEntities();
        if (!users.containsKey(userId)) throw new EntityNotFoundException();
        User user = users.get(userId);
        users.remove(userId);
        boolean isSuccess = writeToFile(users);
        if (isSuccess) return user;
        else throw new EntityCreationException();
    }

    @Override
    public User getUserById(String userId) {
        Map<String, User> users = readAllEntities();
        if (!users.containsKey(userId)) throw new EntityNotFoundException();
        return users.get(userId);
    }
}
