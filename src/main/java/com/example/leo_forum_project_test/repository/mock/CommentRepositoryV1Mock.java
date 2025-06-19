package com.example.leo_forum_project_test.repository.mock;


import com.example.leo_forum_project_test.dto.Comment;
import com.example.leo_forum_project_test.repository.CommentRepositoryV1;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentRepositoryV1Mock implements CommentRepositoryV1 {

    private final Map<Long, Comment> commentMap;
    private static Long lastCreatedCommentId = 0L;

    public CommentRepositoryV1Mock() {
        this.commentMap = new HashMap<>();
    }

    @Override
    public Comment createComment(Comment comment) {
        Long newCommentId = lastCreatedCommentId++;
        comment.setCommentId(newCommentId);
        commentMap.put(newCommentId, comment);
        System.out.println("Создано новое сообщение от с ID: " + newCommentId);
        return comment;
    }

    @Override
    public Comment getCommentById(Long commentId) {
        if (commentMap.containsKey(commentId)) {
            System.out.println("Найден комментарий с ID: " + commentId);
        } else {
            System.out.println("Комментарий с ID: " + commentId + "не найден");
        }
        return commentMap.get(commentId);

    }

    @Override
    public Comment updateCommentById(Long commentId, Comment comment) {
        if (commentMap.containsKey(comment.getCommentId())) {
            commentMap.put(commentId, comment);
            System.out.println("Был обновлен комментарий с ID: " + comment.getCommentId());
        }
        return comment;
    }

    @Override
    public void deleteCommentById(Long commentId) {
        if (commentMap.remove(commentId) != null) {
            System.out.println("Было удален комментарий с ID: " + commentId);
        } else {
            System.out.println("Комментарий с ID: " + commentId + "не найден");
        }
    }
}
