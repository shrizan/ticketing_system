package com.lambton.model.project;

import com.lambton.common.model.*;
import com.lambton.common.util.AppUtil;
import com.lambton.enums.project.ProjectStatus;
import com.lambton.enums.project.ProjectType;
import com.lambton.model.comment.Comment;
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
    private Project parent;
    private List<User> team = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    public Project(String title, String description, ProjectType projectType, ProjectStatus projectStatus, Project parent) {
        this.title = title;
        this.description = description;
        this.projectType = projectType;
        this.projectStatus = projectStatus;
        this.parent = parent;
    }
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return AppUtil.formatString(
                30,
                title,
                description,
                projectStatus.toString(),
                null == startDate ? "" : startDate.toString(),
                null == endDate ? "" : endDate.toString()
        );
    }
}
