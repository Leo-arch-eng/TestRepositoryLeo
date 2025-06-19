package com.example.leo_forum_project_test.mapper;

import com.example.leo_forum_project_test.dto.AuthorDto;
import com.example.leo_forum_project_test.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDto toDto(Author author);

    Author toEntity(AuthorDto authorDto);
}