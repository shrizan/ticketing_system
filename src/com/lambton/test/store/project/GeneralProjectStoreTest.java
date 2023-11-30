package com.lambton.test.store.project;

import com.lambton.common.AppConstant;
import com.lambton.model.project.GeneralProject;
import com.lambton.model.project.Project;
import com.lambton.store.project.GeneralProjectStore;
import com.lambton.utility.FileUtility;
import com.lambton.utility.FileUtilityImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class GeneralProjectStoreTest {
    private static final String GENERAL_PROJECT_STORE_FILE = "test_general_project_file";
    private final static FileUtility fileUtility = new FileUtilityImpl(AppConstant.GENERAL_PROJECT_STORE_FILE, GENERAL_PROJECT_STORE_FILE);
    private final static GeneralProjectStore store = new GeneralProjectStore(fileUtility);

    @BeforeEach
    public void start() {
        fileUtility.initFiles();
    }

    @AfterEach
    public void remove() {
        fileUtility.deleteFile();
    }

    @Test
    public void createProject() {
        GeneralProject generalProject = new GeneralProject("TITLE", "DESCRIPTION");
        store.createProject(generalProject);
        Project storedProject = store.getProjectById(generalProject.getId());
        Assertions.assertEquals(storedProject.getId(), generalProject.getId());
    }

    @Test
    public void getUser() {
        GeneralProject generalProject = new GeneralProject("SHREEJAN", "ACHARYA");
        store.createProject(generalProject);
        Assertions.assertEquals(generalProject.getId(), store.getProjectById(generalProject.getId()).getId());
    }

    @Test
    public void removeUser() {
        GeneralProject generalProject = new GeneralProject("SHREEJAN", "ACHARYA");
        store.createProject(generalProject);
        store.deleteProject(generalProject.getId());
        Assertions.assertThrows(
                RuntimeException.class,
                () -> store.getProjectById(generalProject.getId())
        );
    }

    @Test
    public void search() {
        GeneralProject generalProject = new GeneralProject("SHREEJAN", "ACHARYA");
        GeneralProject generalProject2 = new GeneralProject("SHREEJAN", "ACHARYA");
        store.createProject(generalProject2);
        store.createProject(generalProject);
        var response = store.search(
                0,
                10,
                Optional.empty()
        );
        Assertions.assertEquals(response.size(), 2);
    }

    @Test
    public void searchByTitle() {
        GeneralProject generalProject = new GeneralProject("Shreejan", "Acharya");
        GeneralProject generalProject2 = new GeneralProject("Alishan", "Gurung");
        store.createProject(generalProject2);
        store.createProject(generalProject);
        var response = store.search(
                0,
                10,
                Optional.of("Alisha")
        );
        Assertions.assertEquals(response.size(), 1);
    }

    @Test
    public void search_moreThan10() {
        for (int i = 0; i < 12; i++) {
            GeneralProject generalProject = new GeneralProject("SHREEJAN", "ACHARYA");
            store.createProject(generalProject);
        }
        var response = store.search(
                1,
                10,
                Optional.empty()
        );
        Assertions.assertEquals(response.size(), 2);
    }
}