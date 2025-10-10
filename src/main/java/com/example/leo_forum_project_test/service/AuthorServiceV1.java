package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.AuthorDto;
import jakarta.validation.Valid;

import java.util.List;

public interface AuthorServiceV1 {

    AuthorDto createAuthor(@Valid AuthorDto authorDto);

    AuthorDto updateAuthor(Long authorId, @Valid AuthorDto authorDto);

    void deleteAuthor(Long authorId);

    AuthorDto findAuthorById(Long authorId);

    List<AuthorDto> findAllAuthors();

    List<AuthorDto> findAllAuthorsPaginated(int page, int size);

}