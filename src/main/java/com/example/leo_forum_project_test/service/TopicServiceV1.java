package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.TopicDto;
import com.example.leo_forum_project_test.dto.TopicCompositeDto;
import jakarta.validation.Valid;

import java.util.List;

public interface TopicServiceV1 {

    TopicDto create(@Valid TopicDto topicDto);

    TopicDto findById(Long topicId);

    TopicDto updateById(Long topicId, @Valid TopicDto topicDto);

    void deleteById(Long topicId);

    List<TopicDto> findAllTopic();

    List<TopicDto> findAllTopicsPaginated(int page, int size, String sortBy, String sortDir);

    TopicCompositeDto getTopicWithMessages(Long topicId);
}