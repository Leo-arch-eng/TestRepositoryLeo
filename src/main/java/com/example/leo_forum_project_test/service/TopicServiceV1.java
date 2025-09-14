package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.TopicDto;
import jakarta.validation.Valid;

public interface TopicServiceV1 {

    TopicDto create(@Valid TopicDto topicDto);

    TopicDto findById(Long topicId);

    TopicDto updateById(Long topicId, @Valid TopicDto topicDto);

    void deleteById(Long topicId);
}