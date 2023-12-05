package com.lambton.test.store.user;


import com.lambton.common.*;
import com.lambton.model.user.*;
import com.lambton.store.user.*;
import com.lambton.utility.*;
import org.junit.jupiter.api.*;

import java.util.*;

class DevStoreTest {
    private static final String GENERAL_PROJECT_STORE_FILE = "test_general_project_file";
    private final static FileUtilityImpl<Dev> fileUtility = new FileUtilityImpl<>(AppConstant.DEV_USER_STORE_FILE, GENERAL_PROJECT_STORE_FILE);
    private final static DevUserStore store = new DevUserStore(fileUtility);

    @BeforeEach
    public void start() {
        fileUtility.initFiles();
    }

    @AfterEach
    public void remove() {
        fileUtility.deleteFile();
    }

    @Test
    public void createDevUser() {
        com.lambton.model.user.Dev devUser = new com.lambton.model.user.Dev("TITLE", "DESCRIPTION");
        store.createEntity(devUser);
        com.lambton.model.user.Dev storedDevUser = store.getEntity(devUser.getId());
        Assertions.assertEquals(storedDevUser.getId(), devUser.getId());
    }

    @Test
    public void getUser() {
        com.lambton.model.user.Dev devUser = new com.lambton.model.user.Dev("SHREEJAN", "ACHARYA");
        store.createEntity(devUser);
        Assertions.assertEquals(devUser.getId(), store.getEntity(devUser.getId()).getId());
    }

    @Test
    public void removeUser() {
        com.lambton.model.user.Dev devUser = new com.lambton.model.user.Dev("SHREEJAN", "ACHARYA");
        store.createEntity(devUser);
        store.deleteEntity(devUser.getId());
        Assertions.assertNull(store.getEntity(devUser.getId()));
    }

    @Test
    public void search() {
        com.lambton.model.user.Dev devUser = new com.lambton.model.user.Dev("SHREEJAN", "ACHARYA");
        com.lambton.model.user.Dev devUser2 = new com.lambton.model.user.Dev("SHREEJAN", "ACHARYA");
        store.createEntity(devUser2);
        store.createEntity(devUser);
        var response = store.search(
                0,
                10,
                Optional.empty(),
                Optional.empty()
        );
        Assertions.assertEquals(response.size(), 2);
    }

    @Test
    public void searchByFirstAndLastName() {
        com.lambton.model.user.Dev devUser1 = new com.lambton.model.user.Dev("Shreejan", "Acharya");
        com.lambton.model.user.Dev devUser2 = new com.lambton.model.user.Dev("Alishan", "Gurung");
        store.createEntity(devUser2);
        store.createEntity(devUser1);
        var response = store.search(
                0,
                10,
                Optional.of("Alisha"),
                Optional.of("Gurung")
        );
        Assertions.assertEquals(response.size(), 1);
    }

    @Test
    public void search_moreThan10() {
        for (int i = 0; i < 12; i++) {
            Dev devUser = new Dev("SHREEJAN", "ACHARYA");
            store.createEntity(devUser);
        }
        var response = store.search(
                1,
                10,
                Optional.empty(),
                Optional.empty()
        );
        Assertions.assertEquals(response.size(), 2);
    }
}