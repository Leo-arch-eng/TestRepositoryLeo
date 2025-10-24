package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.MessageDto;
import com.example.leo_forum_project_test.dto.TopicDto;
import com.example.leo_forum_project_test.dto.TopicCompositeDto;
import com.example.leo_forum_project_test.entity.MessageE;
import com.example.leo_forum_project_test.entity.TopicE;
import com.example.leo_forum_project_test.mapper.TopicMapper;
import com.example.leo_forum_project_test.repository.MessageRepositoryV2;
import com.example.leo_forum_project_test.repository.TopicRepositoryV2;
import com.example.leo_forum_project_test.validator.TopicValidatorV1;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicServiceV1Impl implements TopicServiceV1 {

    private final TopicRepositoryV2 topicRepositoryV2;
    private final TopicValidatorV1 topicValidatorV1;
    private final TopicMapper topicMapper;
    private final MessageRepositoryV2 messageRepositoryV2;

    @Override
    public TopicDto create(TopicDto topicDto) {
        log.info("Создание нового топика DTO: {}", topicDto);

        TopicE topicE = topicMapper.toEntity(topicDto);

        List<String> errors = topicValidatorV1.validateCreate(topicE);
        if (!errors.isEmpty()) {
            log.error("Ошибки валидации: {}", errors);
            throw new ValidationException(String.join("; ", errors));
        }

        TopicE createdTopicE = topicRepositoryV2.save(topicE);
        log.info("Топик создан успешно с ID: {}", createdTopicE.getId());

        return topicMapper.toDto(createdTopicE);
    }

    @Override
    public TopicDto findById(Long topicId) {
        log.info("Начинается поиск топика по идентификатору: {}", topicId);

        List<String> errors = topicValidatorV1.validateFindById(topicId);
        if (!errors.isEmpty()) {
            log.error("Ошибка валидации : {}", errors);
            throw new ValidationException(String.join("; ", errors));
        }

        Optional<TopicE> optionalTopic = topicRepositoryV2.findById(topicId);
        if (optionalTopic.isEmpty()) {
            log.warn("Топик с идентификатором = {} не найден", topicId);
            throw new ValidationException("Топик с id " + topicId + " не найден");
        }

        log.info("Топик с идентификатором {} успешно найден", topicId);
        return topicMapper.toDto(optionalTopic.get());
    }

    @Override
    public TopicDto updateById(Long topicId, TopicDto topicDto) {
        log.info("Обновление топика с идентификатором: {}, DTO: {}", topicId, topicDto);

        TopicE topicEForValidation = topicMapper.toEntity(topicDto);
        List<String> errors = topicValidatorV1.validateUpdateById(topicId, topicEForValidation);
        if (!errors.isEmpty()) {
            log.error("Ошибки валидации при обновлении топика: {}", errors);
            throw new ValidationException(String.join("; ", errors));
        }

        Optional<TopicE> existingTopicOpt = topicRepositoryV2.findById(topicId);
        if (existingTopicOpt.isEmpty()) {
            log.warn("Топик с идентификатором {} не найден", topicId);
            throw new ValidationException("Топик с id " + topicId + " не найден");
        }

        TopicE existingTopicE = existingTopicOpt.get();
        existingTopicE.setTitle(topicDto.getTitle());
        existingTopicE.setDescription(topicDto.getDescription());

        TopicE updatedTopicE = topicRepositoryV2.save(existingTopicE);

        log.info("Топик с идентификатором {} обновлен успешно", topicId);

        return topicMapper.toDto(updatedTopicE);
    }

    @Override
    public void deleteById(Long topicId) {
        log.info("Начинается поиск топика для удаления по идентификатору: {}", topicId);

        List<String> errors = topicValidatorV1.validateDeleteById(topicId);
        if (!errors.isEmpty()) {
            log.error("Ошибки валидации при удалении топика: {}", errors);
            throw new ValidationException(String.join("; ", errors));
        }

        Optional<TopicE> existingTopic = topicRepositoryV2.findById(topicId);
        if (existingTopic.isEmpty()) {
            log.warn("Топик с идентификатором {} не найден для удаления", topicId);
            throw new ValidationException("Топик с id " + topicId + " не найден");
        }

        topicRepositoryV2.deleteById(topicId);
        log.info("Топик с ID {} удален успешно", topicId);
    }

    @Override
    public List<TopicDto> findAllTopic() {
        log.info("Начинается поиск всех топиков");
        List<TopicE> topics = topicRepositoryV2.findAll();
        if (topics.isEmpty()) {
            log.warn("Список топиков пуст");
            throw new ValidationException("Список топиков " + topics + "не найден");
        }
        log.info("Список топиков успешно найден");
        return topics.stream()
                .map(topicMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TopicDto> findAllTopicsPaginated(
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

        Page<TopicE> pageTopics = topicRepositoryV2.findAll(pageable);

        if (pageTopics.isEmpty()) {
            log.warn("Список топиков пуст");
            throw new ValidationException("Топики не найдены");
        }

        log.info("Обнаружено {} топиков", pageTopics.getTotalElements());

        return pageTopics.getContent()
                .stream()
                .map(topicMapper::toDto)
                .collect(Collectors.toList());
    }

    // Вспомогательный метод для проверки полей сортировки
    private boolean isFieldSortable(String fieldName) {
        return "id".equals(fieldName) ||
                "title".equals(fieldName) ||
                "description".equals(fieldName);
    }
    public TopicCompositeDto getTopicWithMessages(Long topicId) {
        // Находим топик
        TopicE topic = topicRepositoryV2.findById(topicId)
                .orElseThrow(() -> new ValidationException("Топик с id " + topicId + " не найден"));

        // Получаем сообщения для этого топика
        List<MessageE> messageEntity = messageRepositoryV2.findByTopicId(topicId);

        // Преобразуем сообщения в DTO
        List<MessageDto> messageDto = messageEntity.stream()
                .map(msg -> new MessageDto(
                        msg.getMessageId(),
                        msg.getAuthorName(),
                        msg.getAuthorSurname(),
                        msg.getMessage(),
                        msg.getLocalDateTime().toString(),
                        msg.getTopicId()
                ))
                .collect(Collectors.toList());

        // Создаем и возвращаем DTO топика с сообщениями
        return new TopicCompositeDto(
                topic.getId(),
                topic.getTitle(),
                topic.getDescription(),
                messageDto
        );
    }
}
