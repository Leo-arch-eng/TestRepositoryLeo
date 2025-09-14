package com.example.leo_forum_project_test.repository.mock;


import com.example.leo_forum_project_test.entity.CommentE;
import com.example.leo_forum_project_test.repository.CommentRepositoryV1;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommentRepositoryV1Mock implements CommentRepositoryV1 {

    private final Map<Long, CommentE> commentMap;
    private static Long lastCreatedCommentId = 0L;

    public CommentRepositoryV1Mock() {
        this.commentMap = new HashMap<>();
    }

    @Override
    public CommentE createComment(CommentE commentE) {
        Long newCommentId = lastCreatedCommentId++;
        commentE.setId(newCommentId);
        commentMap.put(newCommentId, commentE);
        System.out.println("Создан новый комментарий от с ID: " + newCommentId);
        return commentE;
    }

    @Override
    public CommentE getCommentById(Long commentId) {
        if (commentMap.containsKey(commentId)) {
            System.out.println("Найден комментарий с ID: " + commentId);
        } else {
            System.out.println("Комментарий с ID: " + commentId + "не найден");
        }
        return commentMap.get(commentId);

    }

    @Override
    public CommentE updateCommentById(Long commentId, CommentE commentE) {
        if (commentMap.containsKey(commentE.getId())) {
            commentMap.put(commentId, commentE);
            System.out.println("Был обновлен комментарий с ID: " + commentE.getId());
        }
        return commentE;
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
