package com.example.leo_forum_project_test.mapper;

import com.example.leo_forum_project_test.dto.TopicDto;
import com.example.leo_forum_project_test.entity.TopicE;

public interface TopicMapper {
    TopicDto toDto(TopicE topicE);
    TopicE toEntity(TopicDto dto);
}