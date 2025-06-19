package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.dto.Topic;

public interface TopicRepositoryV1{
    Topic create(Topic topic);

    Topic findById(Long topicId);

    void deleteById(Long topicId);

    Topic update(Long topicId, Topic topic);


}

