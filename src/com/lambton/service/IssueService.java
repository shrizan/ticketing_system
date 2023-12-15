package com.lambton.service;

import com.lambton.common.AppConstant;
import com.lambton.enums.issue.IssueStatus;
import com.lambton.enums.issue.IssueType;
import com.lambton.enums.issue.Priority;
import com.lambton.enums.user.UserType;
import com.lambton.model.issue.Issue;
import com.lambton.store.issue.IssueStore;
import com.lambton.store.issue.IssueStoreImpl;
import com.lambton.utility.AccountUtility;
import com.lambton.utility.FileUtilityImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IssueService {
    IssueStore<Issue> issueStore = new IssueStoreImpl<>(new FileUtilityImpl<>(AppConstant.ISSUE_FILE, AppConstant.PROJECT_PREFIX));

    public Issue createIssue(Issue issue) {
        if (null == AccountUtility.loggedInUser || AccountUtility.loggedInUser.getUserType() != UserType.MANAGER) {
            System.out.println("User not authorized!!!");
            return null;
        }
        issueStore.createEntity(issue);
        return issue;
    }

    public void updateIssue(String issueId, Issue issue) {
        if (null == AccountUtility.loggedInUser || AccountUtility.loggedInUser.getUserType() != UserType.MANAGER) {
            System.out.println("User not authorized!!!");
            return;
        }
        issueStore.updateEntity(issueId, issue);
    }

    public void removeIssue(String issueId, IssueType issueType) {
        if (null == AccountUtility.loggedInUser || AccountUtility.loggedInUser.getUserType() != UserType.MANAGER) {
            System.out.println("User not authorized !!!");
            return;
        }
        issueStore.deleteEntity(issueId);
    }

    public List<Issue> searchIssue(
            long page,
            long size,
            Optional<String> optionalTitle,
            Optional<String> optionalAssigneeId,
            Optional<Priority> optionalPriority,
            Optional<IssueStatus> optionalIssueStatus,
            Optional<IssueType> issueType
    ) {
        return issueStore.search(page, size, optionalTitle, optionalAssigneeId, optionalPriority, optionalIssueStatus, issueType);
    }
}
