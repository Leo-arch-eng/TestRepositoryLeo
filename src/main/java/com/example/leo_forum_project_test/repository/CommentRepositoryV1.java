package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.entity.CommentE;

public interface CommentRepositoryV1 {
    CommentE createComment(CommentE commentE);

    CommentE getCommentById(Long commentId);

    CommentE updateCommentById(Long commentId, CommentE commentE);

    void deleteCommentById(Long commentId);
}
