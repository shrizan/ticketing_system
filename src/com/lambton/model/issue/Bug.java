package com.lambton.model.issue;

import com.lambton.enums.issue.*;
import com.lambton.model.project.Project;
import com.lambton.model.user.User;

public class Bug extends Issue {
    public Bug(String title, String description, Project project, User assignee, Priority priority, IssueStatus issueStatus) {
        super(title, description, project, assignee, IssueType.BUG, priority, issueStatus);
    }
}
