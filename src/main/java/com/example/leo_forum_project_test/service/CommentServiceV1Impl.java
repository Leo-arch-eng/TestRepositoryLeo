package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.CommentDto;
import com.example.leo_forum_project_test.entity.CommentE;
import com.example.leo_forum_project_test.mapper.CommentMapper;
import com.example.leo_forum_project_test.repository.CommentRepositoryV2;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RequiredArgsConstructor
@Service
public class CommentServiceV1Impl implements CommentServiceV1 {

    private final CommentRepositoryV2 commentRepositoryV2;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto createComment(@Valid CommentDto commentDto) {
        log.info("Создание нового комментария DTO: {}", commentDto);
        // Преобразуем DTO в сущность
        CommentE commentEEntity = commentMapper.toEntity(commentDto);
        CommentE savedCommentE = commentRepositoryV2.save(commentEEntity);
        // Возвращаем DTO
        CommentDto savedDto = commentMapper.toDto(savedCommentE);
        log.info("Новый комментарий успешно создан DTO: {}", savedDto);
        return savedDto;
    }

    @Override
    public CommentDto getCommentById(Long commentId) {
        log.info("Получение комментария по ID: {}", commentId);
        Optional<CommentE> optionalComment = commentRepositoryV2.findById(commentId);
        if (optionalComment.isPresent()) {
            CommentDto dto = commentMapper.toDto(optionalComment.get());
            log.info("Комментарий с ID: {} найден DTO: {}", commentId, dto);
            return dto;
        } else {
            log.warn("Комментарий с ID: {} не найден", commentId);
            throw new ValidationException("Комментарий с id " + commentId + " не найден");
        }
    }

    @Override
    public CommentDto updateCommentById(Long commentId, @Valid CommentDto commentDto) {
        log.info("Обновление комментария с ID: {}", commentId);
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
            log.info("Обновление комментария с ID: {} прошло успешно DTO: {}", commentId, updatedDto);
            return updatedDto;
        } else {
            log.warn("Комментарий с ID: {} не найден", commentId);
            throw new ValidationException("Комментарий с id " + commentId + " не найден");
        }
    }

    @Override
    public void deleteCommentById(Long commentId) {
        log.info("Удаление комментария с ID: {}", commentId);
        Optional<CommentE> optionalComment = commentRepositoryV2.findById(commentId);
        if (optionalComment.isPresent()) {
            commentRepositoryV2.deleteById(commentId);
            log.info("Удаление комментария с ID: {} прошло успешно", commentId);
        } else {
            log.warn("Комментарий с ID: {} не найден для удаления", commentId);
            throw new ValidationException("Комментарий с id " + commentId + " не найден");
        }
    }

    @Override
    public List<CommentDto> findAllCommentsPaginated(int page, int size) {
        log.info("Поиск комментариев с пагинацией: страница {}, размер {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentE> pageComments = commentRepositoryV2.findAll(pageable);
        List<CommentE> comments = pageComments.getContent();
        if (comments.isEmpty()) {
            log.warn("Ни одного комментария не найдено на странице");
            throw new ValidationException("Запрашиваемый список комментариев пуст");
        }
        return comments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}