package com.lambton;

import com.lambton.common.util.AppUtil;
import com.lambton.model.comment.Comment;
import com.lambton.utility.AccountUtility;

import java.util.List;

public class CommentInput extends InputUtility {
    static Comment getComment() {
        return new Comment(getString("Enter comment:"), AccountUtility.loggedInUser);
    }

    static void displayComments(List<Comment> comments) {
        System.out.println(AppUtil.formatString(50,"SN", "Comment", "Comment By"));
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            String commentContent =AppUtil.formatString(50,Integer.toString(i + 1), comment.toString());
            System.out.printf("%s\n", commentContent);
        }
    }

    static Comment selectComment(List<Comment> comments) {
        displayComments(comments);
        while (true) {
            int choice = getInt("Select SN Number:");
            if (choice > 0 && choice <= comments.size()) return comments.get(choice - 1);
            else System.out.println("Invalid selection!!!");
        }
    }
}
