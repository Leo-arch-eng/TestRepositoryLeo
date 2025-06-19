package com.example.leo_forum_project_test.mapper;

import com.example.leo_forum_project_test.dto.TopicDto;
import com.example.leo_forum_project_test.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    TopicDto toDto(Topic topic);

    Topic toEntity(TopicDto dto);
}