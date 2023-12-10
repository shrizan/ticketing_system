package com.lambton.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AppConstant {
    public static final String PROJECT_PREFIX = "ticketing_system";
    public static final String PROJECT_STORE_FILE = "project_file";
    public static final String USER_STORE_FILE = "user_file";
    public static final String ISSUE_FILE = "issue_file";

    public static final List<String> FILES = List.of(
            PROJECT_STORE_FILE,
            USER_STORE_FILE,
            ISSUE_FILE
    );

    public static void initFiles() {
        Path userPath = Paths.get(System.getProperty("user.dir"));
        Path appRootFolderPath = Paths.get(userPath.toString(), PROJECT_PREFIX);
        if (!Files.exists(appRootFolderPath)) {
            try {
                Files.createDirectories(appRootFolderPath);
                for (String fileName : FILES) {
                    Path filePath = Paths.get(appRootFolderPath.toString(), fileName);
                    if (!Files.exists(filePath)) {
                        Files.createFile(filePath);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static final long DEFAULT_PAGE = 0;
    public static final long DEFAULT_SIZE = 10;
}
