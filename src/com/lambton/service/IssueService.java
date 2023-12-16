package com.lambton.service;

import com.lambton.common.AppConstant;
import com.lambton.enums.issue.IssueStatus;
import com.lambton.enums.issue.IssueType;
import com.lambton.enums.issue.Priority;
import com.lambton.model.issue.Issue;
import com.lambton.store.issue.IssueStore;
import com.lambton.store.issue.IssueStoreImpl;
import com.lambton.utility.FileUtilityImpl;

import java.util.List;
import java.util.Optional;

public class IssueService extends BaseService<Issue, IssueStore<Issue>> {
    public IssueService() {
        super(new IssueStoreImpl<>(new FileUtilityImpl<>(AppConstant.ISSUE_FILE, AppConstant.PROJECT_PREFIX)));
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
        return store.search(page, size, optionalTitle, optionalAssigneeId, optionalPriority, optionalIssueStatus, issueType);
    }
}
