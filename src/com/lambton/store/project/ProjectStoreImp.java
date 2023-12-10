package com.lambton.store.project;

import com.lambton.common.store.*;
import com.lambton.exception.EntityCreationException;
import com.lambton.exception.EntityExistException;
import com.lambton.exception.EntityNotFoundException;
import com.lambton.model.project.Project;
import com.lambton.utility.FileUtility;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ProjectStoreImp<T extends Project> extends StoreImpl<T> implements ProjectStore<T> {

    public ProjectStoreImp(FileUtility<T> fileUtility) {
        super(fileUtility);
    }

    @Override
    public Map<String,T> allEntities() {
        return fileUtility.readAllEntities();
    }
}
