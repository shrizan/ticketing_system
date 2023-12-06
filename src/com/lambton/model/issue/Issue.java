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
    private User assignee;
    private List<User> assignedTos = new ArrayList<>();
    private IssueType issueType;
    private Priority priority;
    private IssueStatus issueStatus;

    public Issue(String title, String description, Project project, User assignee, IssueType issueType, Priority priority, IssueStatus issueStatus) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.assignee = assignee;
        this.issueType = issueType;
        this.priority = priority;
        this.issueStatus = issueStatus;
    }

    public String getTitle() {
        return title;
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

    public User getAssignee() {
        return assignee;
    }
}
