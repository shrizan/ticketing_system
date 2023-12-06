package com.lambton.model.issue;

import com.lambton.enums.issue.IssueStatus;
import com.lambton.enums.issue.IssueType;
import com.lambton.enums.issue.Priority;
import com.lambton.model.project.Project;
import com.lambton.model.user.User;

public class Task extends Issue{
    public Task(String title, String description, Project project, User assignee, Priority priority, IssueStatus issueStatus) {
        super(title, description, project, assignee, IssueType.TASK, priority, issueStatus);
    }
}
