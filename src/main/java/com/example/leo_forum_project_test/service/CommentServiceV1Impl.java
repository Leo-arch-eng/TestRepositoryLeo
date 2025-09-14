package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.CommentDto;
import com.example.leo_forum_project_test.entity.CommentE;
import com.example.leo_forum_project_test.mapper.CommentMapper;
import com.example.leo_forum_project_test.repository.CommentRepositoryV2;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
@RequiredArgsConstructor
@Service
public class CommentServiceV1Impl implements CommentServiceV1 {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceV1Impl.class);

    private final CommentRepositoryV2 commentRepositoryV2;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto createComment(@Valid CommentDto commentDto) {
        logger.info("Создание нового комментария DTO: {}", commentDto);
        // Преобразуем DTO в сущность
        CommentE commentEEntity = commentMapper.toEntity(commentDto);
        CommentE savedCommentE = commentRepositoryV2.save(commentEEntity);
        // Возвращаем DTO
        CommentDto savedDto = commentMapper.toDto(savedCommentE);
        logger.info("Новый комментарий успешно создан DTO: {}", savedDto);
        return savedDto;
    }

    @Override
    public CommentDto getCommentById(Long commentId) {
        logger.info("Получение комментария по ID: {}", commentId);
        Optional<CommentE> optionalComment = commentRepositoryV2.findById(commentId);
        if (optionalComment.isPresent()) {
            CommentDto dto = commentMapper.toDto(optionalComment.get());
            logger.info("Комментарий с ID: {} найден DTO: {}", commentId, dto);
            return dto;
        } else {
            logger.warn("Комментарий с ID: {} не найден", commentId);
            throw new ValidationException("Комментарий с id " + commentId + " не найден");
        }
    }

    @Override
    public CommentDto updateCommentById(Long commentId, @Valid CommentDto commentDto) {
        logger.info("Обновление комментария с ID: {}", commentId);
        Optional<CommentE> optionalExistingComment = commentRepositoryV2.findById(commentId);
        if (optionalExistingComment.isPresent()) {
            CommentE existingCommentE = optionalExistingComment.get();
            // Преобразуем DTO в сущность, чтобы взять обновлённые данные
            CommentE updatedData = commentMapper.toEntity(commentDto);

            // Обновляем поля существующего комментария
            existingCommentE.setComment(updatedData.getComment());
            existingCommentE.setAuthor(updatedData.getAuthor());
            existingCommentE.setDate(updatedData.getDate());

            CommentE updatedCommentE = commentRepositoryV2.save(existingCommentE);
            CommentDto updatedDto = commentMapper.toDto(updatedCommentE);
            logger.info("Обновление комментария с ID: {} прошло успешно DTO: {}", commentId, updatedDto);
            return updatedDto;
        } else {
            logger.warn("Комментарий с ID: {} не найден", commentId);
            throw new ValidationException("Комментарий с id " + commentId + " не найден");
        }
    }

    @Override
    public void deleteCommentById(Long commentId) {
        logger.info("Удаление комментария с ID: {}", commentId);
        Optional<CommentE> optionalComment = commentRepositoryV2.findById(commentId);
        if (optionalComment.isPresent()) {
            commentRepositoryV2.deleteById(commentId);
            logger.info("Удаление комментария с ID: {} прошло успешно", commentId);
        } else {
            logger.warn("Комментарий с ID: {} не найден для удаления", commentId);
            throw new ValidationException("Комментарий с id " + commentId + " не найден");
        }
    }
}