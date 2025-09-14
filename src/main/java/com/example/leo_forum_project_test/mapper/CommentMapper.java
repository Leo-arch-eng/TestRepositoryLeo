package com.example.leo_forum_project_test.mapper;

import com.example.leo_forum_project_test.dto.CommentDto;
import com.example.leo_forum_project_test.entity.CommentE;

public interface CommentMapper {
    CommentDto toDto(CommentE commentE);
    CommentE toEntity(CommentDto commentDto);
}