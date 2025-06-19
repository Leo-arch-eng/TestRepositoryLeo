package com.example.leo_forum_project_test.service;


import com.example.leo_forum_project_test.dto.Topic;

public interface TopicServiceV1 {

    Topic create(Topic topic);

    Topic findById(Long topicId);

    Topic update(Long topicId, Topic topic);

    void deleteById(Long topicId);
}
