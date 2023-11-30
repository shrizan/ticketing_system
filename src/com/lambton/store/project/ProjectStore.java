package com.lambton.store.project;

import com.lambton.enums.ProjectType;
import com.lambton.model.project.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectStore {
    Project createProject(Project project);

    Project updateProject(String projectId, Project project);

    Project deleteProject(String projectId);

    Project getProjectById(String projectId);
    List<Project> search(int page, int size, Optional<String> title);
}
