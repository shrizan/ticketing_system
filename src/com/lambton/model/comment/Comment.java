package com.lambton.model.comment;

import com.lambton.common.model.BaseModel;
import com.lambton.common.util.AppUtil;
import com.lambton.model.user.User;

public class Comment extends BaseModel {
    private String comment;
    private User commentedBy;

    public Comment(String comment, User commentedBy) {
        this.comment = comment;
        this.commentedBy = commentedBy;
    }

    @Override
    public String toString() {
        return AppUtil.formatString(
                50,
                comment,
                commentedBy == null ? "" : commentedBy.getFullName()
        );
    }

    public User getCommentedBy() {
        return commentedBy;
    }
}
