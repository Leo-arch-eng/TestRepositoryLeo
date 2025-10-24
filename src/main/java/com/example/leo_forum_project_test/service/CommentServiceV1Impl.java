package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.CommentDto;
import com.example.leo_forum_project_test.entity.CommentE;
import com.example.leo_forum_project_test.entity.MessageE;
import com.example.leo_forum_project_test.mapper.CommentMapper;
import com.example.leo_forum_project_test.repository.CommentRepositoryV2;
import com.example.leo_forum_project_test.repository.MessageRepositoryV2;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final MessageRepositoryV2 messageRepositoryV2;
    private final CommentRepositoryV2 commentRepositoryV2;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto createComment(@Valid CommentDto commentDto) {
        Long messageId = commentDto.getMessageId();
        Optional<MessageE> messageOptional= messageRepositoryV2.findById(messageId);
        if(messageOptional.isEmpty()){
            throw new RuntimeException("Сообщение с таким ID: " + messageId + "не найдено");
        }
        MessageE message = messageOptional.get();
        log.info("Создание нового комментария DTO: {}", commentDto);

        CommentE commentEEntity = commentMapper.toEntity(commentDto);
        //установили связь с топиком
        commentEEntity.setMessageId(messageId);
        // Преобразуем DTO в сущность

        CommentE createdCommentE = commentRepositoryV2.save(commentEEntity);
        CommentDto createdCommentDto = commentMapper.toDto(createdCommentE);
        log.info("Новый комментарий успешно создан DTO: {}", createdCommentDto);
        return createdCommentDto;
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
    public List<CommentDto> findAllCommentsPaginated(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        log.info("Поиск комментариев с пагинацией: страница {}, размер {}, сортировка по {}," +
                " направление {}", page, size, sortBy, sortDir);

        if (!isFieldSortable(sortBy)) {
            throw new ValidationException("Недопустимое поле сортировки: " + sortBy);
        }
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortDir)) {
            direction = Sort.Direction.DESC;
        } else if (!"asc".equalsIgnoreCase(sortDir)) {
            throw new ValidationException("Недопустимое направление сортировки: " + sortDir);
        }

        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CommentE> pageComments = commentRepositoryV2.findAll(pageable);

        if (pageComments.isEmpty()) {
            log.warn("Ни одного комментария не найдено на странице");
            throw new ValidationException("Запрашиваемый список комментариев пуст");
        }

        log.info("Обнаружено {} комментариев", pageComments.getTotalElements());

        return pageComments.getContent().stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    // Вспомогательный метод для проверки поля сортировки комментариев
    private boolean isFieldSortable(String fieldName) {
        return "id".equals(fieldName) ||
                "comment".equals(fieldName) ||
                "author".equals(fieldName) ||
                "localDateTime".equals(fieldName);
    }
}