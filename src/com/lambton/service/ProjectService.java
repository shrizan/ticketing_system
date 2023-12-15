package com.lambton.service;

import com.lambton.common.AppConstant;
import com.lambton.enums.project.ProjectType;
import com.lambton.enums.user.UserType;
import com.lambton.model.project.Project;
import com.lambton.store.project.ProjectStore;
import com.lambton.store.project.ProjectStoreImp;
import com.lambton.utility.AccountUtility;
import com.lambton.utility.FileUtilityImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProjectService {
    ProjectStore<Project> projectStore = new ProjectStoreImp<>(new FileUtilityImpl<>(AppConstant.PROJECT_STORE_FILE, AppConstant.PROJECT_PREFIX));


    public void createProject(Project project) {
        /*if (null == AccountUtility.loggedInUser || AccountUtility.loggedInUser.getUserType() != UserType.MANAGER) {
            System.out.println("User not authorized!!!");
            return;
        }*/
        projectStore.createEntity(project);
        System.out.println("New project created !!!");
    }

    public void updateProject(String projectId, Project project) {
        if (null == AccountUtility.loggedInUser || AccountUtility.loggedInUser.getUserType() != UserType.MANAGER) {
            System.out.println("User not authorized!!!");
            return;
        }
        projectStore.updateEntity(projectId, project);
    }

    public void removeProject(String projectId, ProjectType projectType) {
        if (null == AccountUtility.loggedInUser || AccountUtility.loggedInUser.getUserType() != UserType.MANAGER) {
            System.out.println("User not authorized !!!");
            return;
        }
        projectStore.deleteEntity(projectId);
    }

    public List<Project> search(int page, int size, Optional<String> title, Optional<ProjectType> projectType) {
        return projectStore.search(page, size, title, projectType);
    }
}
