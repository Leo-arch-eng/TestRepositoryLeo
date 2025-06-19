package com.example.leo_forum_project_test.service.mock;

import com.example.leo_forum_project_test.entity.Message;
import com.example.leo_forum_project_test.mapper.MessageMapper;
import com.example.leo_forum_project_test.repository.MessageRepositoryV2;
import com.example.leo_forum_project_test.service.MessageServiceV1;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class MessageServiceV1Mock implements MessageServiceV1 {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceV1Mock.class);

    private final MessageRepositoryV2 messageRepositoryV2;
    private final MessageMapper messageMapper;

    public MessageServiceV1Mock(MessageRepositoryV2 messageRepositoryV2, MessageMapper messageMapper) {
        this.messageRepositoryV2 = messageRepositoryV2;
        this.messageMapper = messageMapper;
    }


    @Override
    public Message createMessage(@Valid Message message) {
        logger.info("Создание нового сообщения: {}", message);
        Message createdMessage = messageRepositoryV2.save(message);
        logger.info("Успешно создано сообщение: {}", createdMessage);
        return createdMessage;
    }

    @Override
    public Message findMessageById(Long messageId) {
        logger.info("Поиск сообщения с ID: {}", messageId);
        Optional<Message> optionalMessage = messageRepositoryV2.findById(messageId);
        if (optionalMessage.isPresent()) {
            logger.info("Найдено сообщение с ID: {}", messageId);
            return optionalMessage.get();
        } else {
            logger.warn("Сообщение с ID: {} не найдено", messageId);
            throw new ValidationException("Сообщение с id " + messageId + " не найдено");
        }
    }

    @Override
    public Message updateMessage(Long messageId, @Valid Message message) {
        logger.info("Обновление сообщения с ID: {}", messageId);
        Optional<Message> optionalExistingMessage = messageRepositoryV2.findById(messageId);
        if (optionalExistingMessage.isPresent()) {
            Message existingMessage = optionalExistingMessage.get();

            existingMessage.setAuthorName(message.getAuthorName());
            existingMessage.setAuthorSurname(message.getAuthorSurname());
            existingMessage.setMessage(message.getMessage());
            existingMessage.setLocalDate(message.getLocalDate());

            Message updatedMessage = messageRepositoryV2.save(existingMessage);
            logger.info("Обновление сообщения с ID: {} прошло успешно", messageId);
            return updatedMessage;
        } else {
            logger.warn("Ошибка, сообщение с ID: {} не найдено", messageId);
            throw new ValidationException("Сообщение с id " + messageId + " не найдено");
        }
    }

    @Override
    public void deleteMessage(Long messageId) {
        logger.info("Удаление сообщения по ID: {}", messageId);
        Optional<Message> optionalMessage = messageRepositoryV2.findById(messageId);
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