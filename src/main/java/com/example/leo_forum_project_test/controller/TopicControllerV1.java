package com.example.leo_forum_project_test.controller;

import com.example.leo_forum_project_test.dto.TopicDto;
import com.example.leo_forum_project_test.service.TopicServiceV1;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topic")
public class TopicControllerV1 {

    @Autowired
    private final TopicServiceV1 topicServiceV1;

    public TopicControllerV1(TopicServiceV1 topicServiceV1) {
        this.topicServiceV1 = topicServiceV1;
    }

    @GetMapping("/{topic_id}")
    public ResponseEntity<TopicDto> findTopicById(@PathVariable(name = "topic_id") Long topicId) {
        try {
            TopicDto topicDto = topicServiceV1.findById(topicId);
            return ResponseEntity.ok(topicDto);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null); // Или лучше обработать конкретные исключения
        }
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createTopic(@RequestBody @Valid TopicDto topicDto) {
        try {
            TopicDto createdTopic = topicServiceV1.create(topicDto);
            return ResponseEntity.ok(createdTopic);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Некорректные данные при создании Топика");
        }
    }

    @PutMapping("/{topic_id}")
    public ResponseEntity<?> updateTopic(
            @PathVariable(name = "topic_id") Long topicId,
            @RequestBody @Valid TopicDto topicDto
    ) {
        try {
            TopicDto updatedTopic = topicServiceV1.updateById(topicId, topicDto);
            return ResponseEntity.ok(updatedTopic);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Некорректные данные при обновлении топика");
        }
    }

    @DeleteMapping("/{topic_id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "topic_id") Long topicId) {
        try {
            topicServiceV1.deleteById(topicId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка при удалении топика");
        }
    }
    @GetMapping("/allTopics")
    public ResponseEntity<List<TopicDto>> getAllTopicsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<TopicDto> topics = topicServiceV1.findAllTopicsPaginated(page, size);
            return ResponseEntity.ok(topics);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}