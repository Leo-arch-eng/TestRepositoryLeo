package com.example.leo_forum_project_test.mapper;

import com.example.leo_forum_project_test.dto.MessageDto;
import com.example.leo_forum_project_test.entity.MessageE;

public interface MessageMapper {
    MessageDto toDto(MessageE messageE);
    MessageE toEntity(MessageDto messageDto);
}