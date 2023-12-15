package com.lambton.test.store.project;

/*

class GeneralProjectStoreTest {
    private static final String GENERAL_PROJECT_STORE_FILE = "test_general_project_file";
    private final static FileUtilityImpl<Dev> fileUtility = new FileUtilityImpl(AppConstant.GENERAL_PROJECT_STORE_FILE, GENERAL_PROJECT_STORE_FILE);
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
        Dev generalProject = new Dev("TITLE", "DESCRIPTION");
        store.createEntity(generalProject);
        Project storedProject = store.getEntity(generalProject.getId());
        Assertions.assertEquals(storedProject.getId(), generalProject.getId());
    }

    @Test
    public void getUser() {
        Dev generalProject = new Dev("SHREEJAN", "ACHARYA");
        store.createEntity(generalProject);
        Assertions.assertEquals(generalProject.getId(), store.getEntity(generalProject.getId()).getId());
    }

    @Test
    public void removeUser() {
        Dev generalProject = new Dev("SHREEJAN", "ACHARYA");
        store.createEntity(generalProject);
        store.deleteEntity(generalProject.getId());
        Assertions.assertNull(store.getEntity(generalProject.getId()));
    }

    @Test
    public void search() {
        Dev generalProject = new Dev("SHREEJAN", "ACHARYA");
        Dev generalProject2 = new Dev("SHREEJAN", "ACHARYA");
        store.createEntity(generalProject2);
        store.createEntity(generalProject);
        var response = store.search(
                0,
                10,
                Optional.empty()
        );
        Assertions.assertEquals(response.size(), 2);
    }

    @Test
    public void searchByTitle() {
        Dev generalProject = new Dev("Shreejan", "Acharya");
        Dev generalProject2 = new Dev("Alishan", "Gurung");
        store.createEntity(generalProject2);
        store.createEntity(generalProject);
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
            Dev generalProject = new Dev("SHREEJAN", "ACHARYA");
            store.createEntity(generalProject);
        }
        var response = store.search(
                1,
                10,
                Optional.empty()
        );
        Assertions.assertEquals(response.size(), 2);
    }
}*/
