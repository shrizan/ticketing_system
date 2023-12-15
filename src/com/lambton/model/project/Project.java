package com.lambton.model.project;

import com.lambton.common.model.*;
import com.lambton.enums.project.ProjectStatus;
import com.lambton.enums.project.ProjectType;
import com.lambton.model.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project extends BaseModel {
    private String title;
    private String description;
    private ProjectType projectType;
    private ProjectStatus projectStatus;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate;
    private List<User> team = new ArrayList<>();

    public Project(String title, String description, ProjectType projectType, ProjectStatus projectStatus) {
        this.title = title;
        this.description = description;
        this.projectType = projectType;
        this.projectStatus = projectStatus;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getTeam() {
        return team;
    }
}
