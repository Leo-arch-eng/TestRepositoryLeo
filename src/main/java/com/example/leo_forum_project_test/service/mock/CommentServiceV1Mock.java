package com.example.leo_forum_project_test.service.mock;

import com.example.leo_forum_project_test.entity.Comment;
import com.example.leo_forum_project_test.mapper.CommentMapper;
import com.example.leo_forum_project_test.repository.CommentRepositoryV2;
import com.example.leo_forum_project_test.service.CommentServiceV1;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
@Service
public class CommentServiceV1Mock implements CommentServiceV1 {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceV1Mock.class);

    private final CommentRepositoryV2 commentRepositoryV2;
    private final CommentMapper commentMapper;

    public CommentServiceV1Mock(CommentRepositoryV2 commentRepositoryV2, CommentMapper commentMapper) {
        this.commentRepositoryV2 = commentRepositoryV2;
        this.commentMapper = commentMapper;
    }

    @Override
    public Comment createComment(@Valid Comment comment) {
        logger.info("Создание нового комментария: {}", comment);
        Comment savedComment = commentRepositoryV2.save(comment);
        logger.info("Новый комментарий успешно создан: {}", savedComment);
        return savedComment;
    }

    @Override
    public Comment getCommentById(Long commentId) {
        logger.info("Получение комментария по ID: {}", commentId);
        Optional<Comment> optionalComment = commentRepositoryV2.findById(commentId);
        if (optionalComment.isPresent()) {
            logger.info("Комментарий с ID: {} найден", commentId);
            return optionalComment.get();
        } else {
            logger.warn("Комментарий с ID: {} не найден", commentId);
            throw new ValidationException("Комментарий с id " + commentId + " не найден");
        }
    }

    @Override
    public Comment updateCommentById(Long commentId, @Valid Comment comment) {
        logger.info("Обновление комментария с ID: {}", commentId);
        Optional<Comment> optionalExistingComment = commentRepositoryV2.findById(commentId);
        if (optionalExistingComment.isPresent()) {
            Comment existingComment = optionalExistingComment.get();

            // Обновляем поля
            existingComment.setComment(comment.getComment());
            existingComment.setAuthor(comment.getAuthor());
            existingComment.setDate(comment.getDate());

            Comment updatedComment = commentRepositoryV2.save(existingComment);
            logger.info("Обновление комментария с ID: {} прошло успешно", commentId);
            return updatedComment;
        } else {
            logger.warn("Комментарий с ID: {} не найден", commentId);
            throw new ValidationException("Комментарий с id " + commentId + " не найден");
        }
    }

    @Override
    public void deleteCommentById(Long commentId) {
        logger.info("Удаление комментария с ID: {}", commentId);
        Optional<Comment> optionalComment = commentRepositoryV2.findById(commentId);
        if (optionalComment.isPresent()) {
            commentRepositoryV2.deleteById(commentId);
            logger.info("Удаление комментария с ID: {} прошло успешно", commentId);
        } else {
            logger.warn("Комментарий с ID: {} не найден для удаления", commentId);
            throw new ValidationException("Комментарий с id " + commentId + " не найден");
        }
    }
}