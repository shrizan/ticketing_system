package com.lambton.service;

import com.lambton.common.AppConstant;
import com.lambton.enums.project.ProjectType;
import com.lambton.model.project.Project;
import com.lambton.store.project.ProjectStore;
import com.lambton.store.project.ProjectStoreImp;
import com.lambton.utility.FileUtilityImpl;

import java.util.List;
import java.util.Optional;

public class ProjectService extends BaseService<Project, ProjectStore<Project>> {
    public ProjectService() {
        super(new ProjectStoreImp<>(new FileUtilityImpl<>(AppConstant.PROJECT_STORE_FILE, AppConstant.PROJECT_PREFIX)));
    }

    public List<Project> search(long page, long size, Optional<String> title, Optional<ProjectType> projectType) {
        return store.search(page, size, title, projectType);
    }
}
