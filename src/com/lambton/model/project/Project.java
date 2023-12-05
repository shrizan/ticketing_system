package com.lambton.model.project;

import com.lambton.common.model.*;
import com.lambton.enums.project.ProjectType;

import java.time.LocalDate;

public class Project extends BaseModel {
    private String title;
    private String description;
    private ProjectType projectType;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate;

    public Project(String title, String description, ProjectType projectType) {
        this.title = title;
        this.description = description;
        this.projectType = projectType;
    }

    public String getTitle() {
        return title;
    }

    public ProjectType getProjectType() {
        return projectType;
    }
}
