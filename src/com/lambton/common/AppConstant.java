package com.lambton.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AppConstant {
    public static final String PROJECT_PREFIX = "ticketing_system";
    public static final String GENERAL_PROJECT_STORE_FILE = "general_project_file";
    public static final String ENHANCE_PROJECT_STORE_FILE = "enhancement_project_file";
    public static final String MANAGER_USER_STORE_FILE = "manager_user_file";
    public static final String DEV_USER_STORE_FILE = "dev_user_file";
    public static final String QA_USER_STORE_FILE = "qa_user_file";
    public static final String STORY_ISSUE_FILE = "story_issue_file";
    public static final String TASK_ISSUE_FILE = "task_issue_file";
    public static final String BUG_ISSUE_FILE = "bug_issue_file";

    public static final List<String> FILES = List.of(
            GENERAL_PROJECT_STORE_FILE,
            ENHANCE_PROJECT_STORE_FILE,
            MANAGER_USER_STORE_FILE,
            DEV_USER_STORE_FILE,
            QA_USER_STORE_FILE,
            STORY_ISSUE_FILE,
            TASK_ISSUE_FILE,
            BUG_ISSUE_FILE
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
}
