package com.example.leo_forum_project_test.service.mock;

import com.example.leo_forum_project_test.entity.Topic;
import com.example.leo_forum_project_test.mapper.TopicMapper;
import com.example.leo_forum_project_test.repository.TopicRepositoryV2;
import com.example.leo_forum_project_test.service.TopicServiceV1;
import com.example.leo_forum_project_test.validator.TopicValidatorV1;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TopicServiceV1Mock implements TopicServiceV1 {

    private static final Logger logger = LoggerFactory.getLogger(TopicServiceV1Mock.class);

    private final TopicRepositoryV2 topicRepositoryV2;
    private final TopicValidatorV1 topicValidatorV1;
    private final TopicMapper topicMapper;

    @Override
    public Topic create(Topic topic) {
        logger.info("Создание нового топика : {}", topic);
        List<String> errors = topicValidatorV1.validateCreate(topic);
        if (errors.isEmpty()) {
            logger.info("Создание топика: {}", topic.toString());
            Topic createdTopic = topicRepositoryV2.save(topic);
            logger.info("Топик создан успешно");
            return createdTopic;
        }
        logger.error("Ошибки валидации: {}", errors);
        throw new ValidationException(String.join("; ", errors));
    }

    @Override
    public Topic findById(Long topicId) {
        logger.info("Начинается поиск топика по идентификатору: {}", topicId);
        List<String> errors = topicValidatorV1.validateFindById(topicId);
        if (errors.isEmpty()) {
            logger.info("Поиск топика по идентификатору: {}", topicId);
            Optional<Topic> optionalTopic = topicRepositoryV2.findById(topicId);
            if (optionalTopic.isPresent()) {
                logger.info("Топик с идентификатоором: {} успешно найден", topicId);
                return optionalTopic.get();
            } else {
                logger.warn("Топик с идентификатором = {} не найден", topicId);
                throw new ValidationException("Топик с id " + topicId + " не найден");
            }
        }
        logger.error("Ошибка валидации : {}", errors);
        throw new ValidationException(String.join("; ", errors));
    }

    @Override
    public Topic updateById(Long topicId, Topic topic) {
        logger.info(
                "Поиск топика по идентификатору: {} и валидация по всех атрибутов топика:{}",
                topicId,
                topic
        );
        List<String> errors = topicValidatorV1.validateUpdateById(topicId, topic);
        if (errors.isEmpty()) {
            logger.info("Обновление топика с идентификатором {}:{}", topicId, topic);
            Optional<Topic> existingTopic = topicRepositoryV2.findById(topicId);
            if (existingTopic.isPresent()) {
                Topic topicToUpdate = existingTopic.get();
                topicToUpdate.setTitle(topic.getTitle());
                topicToUpdate.setDescription(topic.getDescription());

                Topic updatedTopic = topicRepositoryV2.save(topicToUpdate);
                logger.info("Топик с идентификатором {} обновлен успешно", topicId);
                return updatedTopic;
            } else {
                logger.warn("Топик с идентификатором {} не найден", topicId);
                throw new ValidationException("Топик с id " + topicId + " не найден");
            }
        }
        logger.error("Ошибки валидации при обновлении топика: {}", errors);
        throw new ValidationException(String.join("; ", errors));
    }

    @Override
    public void deleteById(Long topicId) {
        logger.info("Начинается поиск топика для удаления по идентификатору: {}", topicId);
        List<String> errors = topicValidatorV1.validateDeleteById(topicId);

        if (errors.isEmpty()) {
            Optional<Topic> existingTopic = topicRepositoryV2.findById(topicId);
            if (existingTopic.isPresent()) {
                logger.info("Удаление топика по ID: {}", topicId);
                topicRepositoryV2.deleteById(topicId);
                logger.info("Топик с ID {} удален успешно", topicId);
            } else {
                logger.warn("Топик с идентификатором {} не найден для удаления", topicId);
                throw new ValidationException("Топик с id " + topicId + " не найден");
            }
        } else {
            logger.error("Ошибки валидации при удалении топика: {}", errors);
            throw new ValidationException(String.join("; ", errors));
        }
    }
}