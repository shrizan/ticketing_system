package com.lambton.store.issue;

import com.lambton.enums.issue.*;
import com.lambton.model.issue.*;
import com.lambton.utility.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class StoryStore extends IssueStoreImpl<Story> {
    public StoryStore(FileUtility<Story> fileUtility) {
        super(fileUtility);
    }

    @Override
    public List<Story> search(
            long page,
            long size,
            Optional<String> optionalTitle,
            Optional<String> optionalAssigneeId,
            Optional<Priority> optionalPriority,
            Optional<IssueStatus> optionalIssueStatus
    ) {
        return searchStream(
                page,
                size,
                optionalTitle,
                optionalAssigneeId,
                optionalPriority,
                optionalIssueStatus
        )
                .filter(filterByIssueType())
                .collect(Collectors.toList());
    }

    private static Predicate<Story> filterByIssueType() {
        return issue -> issue.getIssueType().equals(IssueType.STORY);
    }


}
