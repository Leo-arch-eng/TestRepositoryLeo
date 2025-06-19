package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.Comment;

public interface CommentServiceV1 {
    Comment createComment(Comment comment);

    Comment getCommentById(Long commentId);

    Comment updateCommentById(Long commentId, Comment comment);

    void deleteCommentById(Long commentId);

}
