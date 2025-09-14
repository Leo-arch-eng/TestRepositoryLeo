package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.entity.TopicE;

public interface TopicRepositoryV1{
    TopicE create(TopicE topicE);

    TopicE findById(Long topicId);

    void deleteById(Long topicId);

    TopicE update(Long topicId, TopicE topicE);


}

