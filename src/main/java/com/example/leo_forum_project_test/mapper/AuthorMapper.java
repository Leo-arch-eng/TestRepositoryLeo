package com.example.leo_forum_project_test.mapper;

import com.example.leo_forum_project_test.dto.AuthorDto;
import com.example.leo_forum_project_test.entity.AuthorE;

public interface AuthorMapper {
    AuthorDto toDto(AuthorE authorE);
    AuthorE toEntity(AuthorDto authorDto);
}