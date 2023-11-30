package com.lambton.model.project;

import com.lambton.enums.ProjectType;

import java.time.LocalDate;
import java.util.UUID;

public class Project {
    private String id;
    private String title;
    private String description;
    private ProjectType projectType;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate;

    public Project(String title, String description, ProjectType projectType) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.projectType = projectType;
    }

    public String getTitle() {
        return title;
    }
}
