package com.lambton.model.issue;

import com.lambton.common.model.BaseModel;
import com.lambton.enums.issue.IssueStatus;
import com.lambton.enums.issue.IssueType;
import com.lambton.enums.issue.Priority;
import com.lambton.model.project.Project;
import com.lambton.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class Issue extends BaseModel {
    private String title;
    private String description;
    private Project project;
    private Issue parent;
    private User assignedBy;
    private List<User> assignedTos = new ArrayList<>();
    private IssueType issueType;
    private Priority priority;
    private IssueStatus issueStatus;

    public Issue(String title, String description, Project project, User assignedBy, IssueType issueType, Priority priority, IssueStatus issueStatus) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.assignedBy = assignedBy;
        this.issueType = issueType;
        this.priority = priority;
        this.issueStatus = issueStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    public Priority getPriority() {
        return priority;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setParent(Issue parent) {
        this.parent = parent;
    }

    public void setAssignedTos(List<User> assignedTos) {
        this.assignedTos = assignedTos;
    }

    public Project getProject() {
        return project;
    }
}
