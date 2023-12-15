package com.lambton.model.project;

import com.lambton.enums.project.ProjectStatus;
import com.lambton.enums.project.ProjectType;

public class NewProject extends Project {
    public NewProject(String title, String description, ProjectStatus projectStatus) {
        super(title, description, ProjectType.NEW, projectStatus);
    }
}
