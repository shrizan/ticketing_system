package com.lambton.service;

import com.lambton.common.AppConstant;
import com.lambton.enums.project.ProjectType;
import com.lambton.enums.user.UserType;
import com.lambton.model.project.Project;
import com.lambton.store.project.EnhancementProjectStore;
import com.lambton.store.project.GeneralProjectStore;
import com.lambton.store.project.ProjectStore;
import com.lambton.utility.AccountUtility;
import com.lambton.utility.FileUtilityImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProjectService {
    Map<ProjectType, ProjectStore> projectMap = new HashMap<>();

    public ProjectService() {
        projectMap.put(ProjectType.NEW, new GeneralProjectStore(new FileUtilityImpl<>(AppConstant.GENERAL_PROJECT_STORE_FILE, AppConstant.PROJECT_PREFIX)));
        projectMap.put(ProjectType.ENHANCEMENT, new EnhancementProjectStore(new FileUtilityImpl<>(AppConstant.ENHANCE_PROJECT_STORE_FILE, AppConstant.PROJECT_PREFIX)));
    }

    public void createProject(Project project) {
        /*if (null == AccountUtility.loggedInUser || AccountUtility.loggedInUser.getUserType() != UserType.MANAGER) {
            System.out.println("User not authorized!!!");
            return;
        }*/
        projectMap.get(project.getProjectType()).createEntity(project);
        System.out.println("New project created !!!");
    }

    public void updateProject(String projectId, Project project) {
        if (null == AccountUtility.loggedInUser || AccountUtility.loggedInUser.getUserType() != UserType.MANAGER) {
            System.out.println("User not authorized!!!");
            return;
        }
        projectMap.get(project.getProjectType()).updateEntity(projectId, project);
    }

    public void removeProject(String projectId, ProjectType projectType) {
        if (null == AccountUtility.loggedInUser || AccountUtility.loggedInUser.getUserType() != UserType.MANAGER) {
            System.out.println("User not authorized !!!");
            return;
        }
        projectMap.get(projectType).deleteEntity(projectId);
    }

    public List<Project> search(int page, int size, Optional<String> title, ProjectType projectType) {
        List<Project> dd= projectMap.get(projectType).search(page, size, title);
        return dd;
    }
}
