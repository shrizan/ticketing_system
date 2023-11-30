package com.lambton.utility;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.lambton.common.AppConstant.FILES;

public class FileUtilityImpl implements FileUtility {
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

}
