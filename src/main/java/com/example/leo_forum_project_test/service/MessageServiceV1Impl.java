package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.MessageDto;
import com.example.leo_forum_project_test.entity.MessageE;
import com.example.leo_forum_project_test.mapper.MessageMapper;
import com.example.leo_forum_project_test.repository.MessageRepositoryV2;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Optional;

@Validated
@RequiredArgsConstructor
@Service
public class MessageServiceV1Impl implements MessageServiceV1 {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceV1Impl.class);

    private final MessageRepositoryV2 messageRepositoryV2;
    private final MessageMapper messageMapper;

    @Override
    public MessageDto createMessage(@Valid MessageDto messageDto) {
        logger.info("Создание нового сообщения DTO: {}", messageDto);

        MessageE messageEEntity = messageMapper.toEntity(messageDto);

        MessageE createdMessageE = messageRepositoryV2.save(messageEEntity);

        MessageDto createdDto = messageMapper.toDto(createdMessageE);

        logger.info("Успешно создано сообщение DTO: {}", createdDto);
        return createdDto;
    }

    @Override
    public MessageDto findMessageById(Long messageId) {
        logger.info("Поиск сообщения с ID: {}", messageId);
        Optional<MessageE> optionalMessage = messageRepositoryV2.findById(messageId);

        if (optionalMessage.isPresent()) {
            MessageDto dto = messageMapper.toDto(optionalMessage.get());
            logger.info("Найдено сообщение DTO с ID: {}", messageId);
            return dto;
        } else {
            logger.warn("Сообщение с ID: {} не найдено", messageId);
            throw new ValidationException("Сообщение с id " + messageId + " не найдено");
        }
    }

    @Override
    public MessageDto updateMessage(Long messageId, @Valid MessageDto messageDto) {
        logger.info("Обновление сообщения с ID: {}", messageId);
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

            logger.info("Обновление сообщения DTO с ID: {} прошло успешно", messageId);
            return updatedDto;
        } else {
            logger.warn("Ошибка, сообщение с ID: {} не найдено", messageId);
            throw new ValidationException("Сообщение с id " + messageId + " не найдено");
        }
    }

    @Override
    public void deleteMessage(Long messageId) {
        logger.info("Удаление сообщения по ID: {}", messageId);
        Optional<MessageE> optionalMessage = messageRepositoryV2.findById(messageId);

        if (optionalMessage.isPresent()) {
            try {
                messageRepositoryV2.deleteById(messageId);
                logger.info("Успешно удалено сообщение с ID: {}", messageId);
            } catch (Exception e) {
                logger.error("Ошибка при удалении сообщения с ID {}: {}", messageId, e.getMessage());
                throw new RuntimeException("Ошибка при удалении сообщения с id " + messageId, e);
            }
        } else {
            logger.warn("Сообщение с ID: {} не найдено для удаления", messageId);
            throw new ValidationException("Сообщение с id " + messageId + " не найдено");
        }
    }
}