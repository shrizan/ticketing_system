package com.lambton.model.comment;

import com.lambton.common.model.BaseModel;

public class Comment extends BaseModel {
    private String comment;

    public Comment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return comment;
    }
}
