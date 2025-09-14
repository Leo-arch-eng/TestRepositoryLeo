package com.example.leo_forum_project_test.mapper;

import com.example.leo_forum_project_test.dto.TopicDto;
import com.example.leo_forum_project_test.entity.TopicE;
import org.springframework.stereotype.Component;

@Component
public class TopicMapperImpl implements TopicMapper {
    @Override
    public TopicDto toDto(TopicE topicE) {
        return new TopicDto(
                topicE.getId(),
                topicE.getTitle(),
                topicE.getDescription()
        );
    }

    @Override
    public TopicE toEntity(TopicDto dto) {
        return TopicE
                .builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }
}
