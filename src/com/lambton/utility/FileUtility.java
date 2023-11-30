package com.lambton.utility;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileUtility<T> {
    private final String fileName;

    public FileUtility(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, T> readAllEntities() {
        try (
                FileInputStream fileInputStream = new FileInputStream(fileName);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)
        ) {
            return (Map<String, T>) inputStream.readObject();
        } catch (EOFException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    protected boolean writeToFile(Map<String, T> entities) {
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))
        ) {
            outputStream.writeObject(entities);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
