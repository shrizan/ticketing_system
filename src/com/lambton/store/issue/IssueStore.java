package com.lambton.store.issue;


import com.lambton.common.store.*;
import com.lambton.enums.issue.*;
import com.lambton.model.issue.*;

import java.util.*;
import java.util.stream.*;

public interface IssueStore<T extends Issue> extends Store<T> {
    Stream<T> searchStream(
            long page,
            long size,
            Optional<String> optionalTitle,
            Optional<String> optionalAssigneeId,
            Optional<Priority> optionalPriority,
            Optional<IssueStatus> optionalIssueStatus
    );
    List<T> search(
            long page,
            long size,
            Optional<String> optionalTitle,
            Optional<String> optionalAssigneeId,
            Optional<Priority> optionalPriority,
            Optional<IssueStatus> optionalIssueStatus,
            Optional<IssueType> issueType
    );
}
