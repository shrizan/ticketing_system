package com.lambton;

import com.lambton.model.comment.Comment;

import java.util.List;

public class CommentInput extends InputUtility {
    static Comment getComment() {
        return new Comment(getString("Enter comment:"));
    }

    static void displayComments(List<Comment> comments) {
        System.out.println("SN\t\t\tComments");
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            System.out.printf("%s\t\t\t%s\n", i + 1, comment);
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
