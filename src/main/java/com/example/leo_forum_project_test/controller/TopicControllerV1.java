package com.example.leo_forum_project_test.controller;

import com.example.leo_forum_project_test.dto.Topic;
import com.example.leo_forum_project_test.service.TopicServiceV1;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/topic")
public class TopicControllerV1 {

    @Autowired
    private final TopicServiceV1 topicServiceV1;

    public TopicControllerV1(
            TopicServiceV1 topicServiceV1) {
        this.topicServiceV1 = topicServiceV1;
    }

    @GetMapping("/{topic_id}")
    public Topic findTopicById(
            @PathVariable(name = "topic_id")@Min(0)@Max(100) Long topicId
    ) {
        Topic topic = topicServiceV1.findById(topicId);
        return topic;
    }

    @PostMapping
    public Topic createTopic(@RequestBody Topic topic) {
        Topic createdTopic = topicServiceV1.create(topic);
        return createdTopic;
    }

    @PutMapping("/{topic_id}")
    public Topic updateTopic(
            @PathVariable(name = "topic_id") Long topicId,
            @RequestBody Topic topic
    ) {
        Topic updatedTopic = topicServiceV1.update(topicId,topic);
        return updatedTopic;
    }

    @DeleteMapping("/{topic_id}")
    public void deleteById(@PathVariable(name = "topic_id") Long topicId) {
        topicServiceV1.deleteById(topicId);
    }
}


