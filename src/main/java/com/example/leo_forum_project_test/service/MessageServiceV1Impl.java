package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.MessageDto;
import com.example.leo_forum_project_test.entity.MessageE;
import com.example.leo_forum_project_test.mapper.MessageMapper;
import com.example.leo_forum_project_test.repository.MessageRepositoryV2;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RequiredArgsConstructor
@Service
public class MessageServiceV1Impl implements MessageServiceV1 {

    private final MessageRepositoryV2 messageRepositoryV2;
    private final MessageMapper messageMapper;

    @Override
    public MessageDto createMessage(@Valid MessageDto messageDto) {
        log.info("Создание нового сообщения DTO: {}", messageDto);

        MessageE messageEEntity = messageMapper.toEntity(messageDto);

        MessageE createdMessageE = messageRepositoryV2.save(messageEEntity);

        MessageDto createdDto = messageMapper.toDto(createdMessageE);

        log.info("Успешно создано сообщение DTO: {}", createdDto);
        return createdDto;
    }

    @Override
    public MessageDto findMessageById(Long messageId) {
        log.info("Поиск сообщения с ID: {}", messageId);
        Optional<MessageE> optionalMessage = messageRepositoryV2.findById(messageId);

        if (optionalMessage.isPresent()) {
            MessageDto dto = messageMapper.toDto(optionalMessage.get());
            log.info("Найдено сообщение DTO с ID: {}", messageId);
            return dto;
        } else {
            log.warn("Сообщение с ID: {} не найдено", messageId);
            throw new ValidationException("Сообщение с id " + messageId + " не найдено");
        }
    }

    @Override
    public MessageDto updateMessage(Long messageId, @Valid MessageDto messageDto) {
        log.info("Обновление сообщения с ID: {}", messageId);
        Optional<MessageE> optionalExistingMessage = messageRepositoryV2.findById(messageId);

        if (optionalExistingMessage.isPresent()) {
            MessageE existingMessageE = optionalExistingMessage.get();

            existingMessageE.setAuthorName(messageDto.getAuthorName());
            existingMessageE.setAuthorSurname(messageDto.getAuthorSurname());
            existingMessageE.setMessage(messageDto.getMessage());

            if (messageDto.getDate() != null && !messageDto.getDate().isEmpty()) {
                LocalDateTime localDateTime;
                try {
                    localDateTime = LocalDateTime.parse(messageDto.getDate());
                } catch (DataIntegrityViolationException e) {
                    throw new RuntimeException(e);
                }
                existingMessageE.setLocalDateTime(localDateTime);
            }

            MessageE updatedMessageE = messageRepositoryV2.save(existingMessageE);
            MessageDto updatedDto = messageMapper.toDto(updatedMessageE);

            log.info("Обновление сообщения DTO с ID: {} прошло успешно", messageId);
            return updatedDto;
        } else {
            log.warn("Ошибка, сообщение с ID: {} не найдено", messageId);
            throw new ValidationException("Сообщение с id " + messageId + " не найдено");
        }
    }

    @Override
    public void deleteMessage(Long messageId) {
        log.info("Удаление сообщения по ID: {}", messageId);
        Optional<MessageE> optionalMessage = messageRepositoryV2.findById(messageId);

        if (optionalMessage.isPresent()) {
            try {
                messageRepositoryV2.deleteById(messageId);
                log.info("Успешно удалено сообщение с ID: {}", messageId);
            } catch (Exception e) {
                log.error("Ошибка при удалении сообщения с ID {}: {}", messageId, e.getMessage());
                throw new RuntimeException("Ошибка при удалении сообщения с id " + messageId, e);
            }
        } else {
            log.warn("Сообщение с ID: {} не найдено для удаления", messageId);
            throw new ValidationException("Сообщение с id " + messageId + " не найдено");
        }
    }

    @Override
    public List<MessageDto> findAllMessagesPaginated(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
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

        Page<MessageE> messagePage = messageRepositoryV2.findAll(pageable);

        if (messagePage.isEmpty()) {
            log.warn("Список сообщений пуст");
            throw new ValidationException("Сообщения не найдены");
        }

        log.info("Обнаружено {} сообщений", messagePage.getTotalElements());

        return messagePage.getContent()
                .stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    // Вспомогательный метод для проверки поля сортировки
    private boolean isFieldSortable(String fieldName) {
        return "messageId".equals(fieldName) ||
                "authorName".equals(fieldName) ||
                "authorSurname".equals(fieldName) ||
                "message".equals(fieldName) ||
                "localDateTime".equals(fieldName);
    }
}