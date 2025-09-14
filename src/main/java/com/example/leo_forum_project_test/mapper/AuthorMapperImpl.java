package com.example.leo_forum_project_test.mapper;

import com.example.leo_forum_project_test.dto.AuthorDto;
import com.example.leo_forum_project_test.entity.AuthorE;
import org.springframework.stereotype.Service;

@Service
public class AuthorMapperImpl implements AuthorMapper{
    @Override
    public AuthorDto toDto(AuthorE authorE) {
        return new AuthorDto(
                authorE.getId(),
                authorE.getName(),
                authorE.getSurname(),
                authorE.getEmail(),
                authorE.getAge()
        );
    }

    @Override
    public AuthorE toEntity(AuthorDto authorDto) {
        return AuthorE.builder()
                .name(authorDto.getName())
                .age(authorDto.getAge())
                .email(authorDto.getEmail())
                .surname(authorDto.getSurname())
                .build();
    }
}
