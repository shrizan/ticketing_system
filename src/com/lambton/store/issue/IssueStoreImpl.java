package com.lambton.store.issue;

import com.lambton.common.store.*;
import com.lambton.enums.issue.*;
import com.lambton.model.issue.*;
import com.lambton.utility.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class IssueStoreImpl<T extends Issue> extends StoreImpl<T> implements IssueStore<T> {
    public IssueStoreImpl(FileUtility<T> fileUtility) {
        super(fileUtility);
    }

    @Override
    public Map<String, T> allEntities() {
        return null;
    }

    public Stream<T> searchStream(
            long page,
            long size,
            Optional<String> optionalTitle,
            Optional<String> optionalAssigneeId,
            Optional<Priority> optionalPriority,
            Optional<IssueStatus> optionalIssueStatus
    ) {
        return fileUtility
                .readAllEntities()
                .values()
                .stream()
                .skip(page * size)
                .limit(size)
                .filter(filterByTitle(optionalTitle))
                .filter(filterByAssigneeId(optionalAssigneeId))
                .filter(filterByPriority(optionalPriority))
                .filter(filterByIssueStatus(optionalIssueStatus));
    }

    @Override
    public List<T> search(
            long page,
            long size,
            Optional<String> optionalTitle,
            Optional<String> optionalAssigneeId,
            Optional<Priority> optionalPriority,
            Optional<IssueStatus> optionalIssueStatus,
            Optional<IssueType> issueType
    ) {
        return fileUtility
                .readAllEntities()
                .values()
                .stream()
                .skip(page * size)
                .limit(size)
                .filter(filterByTitle(optionalTitle))
                .filter(filterByAssigneeId(optionalAssigneeId))
                .filter(filterByPriority(optionalPriority))
                .filter(filterByIssueStatus(optionalIssueStatus))
                .filter(filterByIssueType(issueType))
                .collect(Collectors.toList());
    }

    private static <T extends Issue> Predicate<T> filterByIssueStatus(Optional<IssueStatus> optionalIssueStatus) {
        return issue -> optionalIssueStatus.map(issueStatus -> issue.getIssueStatus().equals(issueStatus)).orElse(true);
    }

    private static <T extends Issue> Predicate<T> filterByPriority(Optional<Priority> optionalPriority) {
        return issue -> optionalPriority.map(priority -> issue.getPriority().equals(priority)).orElse(true);
    }

    private static <T extends Issue> Predicate<T> filterByAssigneeId(Optional<String> optionalAssigneeId) {
        return issue -> optionalAssigneeId.map(assignedId -> issue.getAssignedBy().getId().equals(assignedId)).orElse(true);
    }

    private static <T extends Issue> Predicate<T> filterByTitle(Optional<String> optionalTitle) {
        return issue -> optionalTitle.map(title -> issue.getTitle().toLowerCase().contains(title.toLowerCase())).orElse(true);
    }

    private static <T extends Issue> Predicate<T> filterByIssueType(Optional<IssueType> optionalIssueType) {
        return issue -> optionalIssueType.map(issueType -> issue.getIssueType().equals(issueType)).orElse(true);
    }
}
