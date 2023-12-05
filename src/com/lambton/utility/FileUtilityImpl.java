package com.lambton.utility;

import com.lambton.common.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.lambton.common.AppConstant.FILES;

public final class FileUtilityImpl<T extends BaseModel> implements FileUtility<T> {
    private final String fileName;
    private final String projectPrefix;
    private final static Path ROOT_FOLDER_PATH = Paths.get(System.getProperty("user.dir"));

    public FileUtilityImpl(String fileName, String projectPrefix) {
        this.fileName = fileName;
        this.projectPrefix = projectPrefix;
    }

    private Path getAppRootPath() {
        return Paths.get(ROOT_FOLDER_PATH.toString(), projectPrefix);
    }

    public void initFiles() {

        try {
            if (!Files.exists(getAppRootPath())) Files.createDirectories(getAppRootPath());
            for (String fileName : FILES) {
                Path filePath = Paths.get(getAppRootPath().toString(), fileName);
                if (!Files.exists(filePath)) {
                    Files.createFile(filePath);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile() {
        try {
            Files.delete(Path.of(getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFilePath() {
        Path filePath = Paths.get(getAppRootPath().toString(), fileName);
        return filePath.toString();
    }

    @Override
    public Map<String, T> readAllEntities() {
        try (
                FileInputStream fileInputStream = new FileInputStream(getFilePath());
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)
        ) {
            return (Map<String, T>) inputStream.readObject();
        } catch (EOFException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean writeToFile(Map<String, T> entities) {
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(getFilePath()))
        ) {
            outputStream.writeObject(entities);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
