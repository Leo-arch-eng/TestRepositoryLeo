package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.Author;

public interface AuthorServiceV1 {

        Author createAuthor(Author author);

        Author updateAuthor(Long authorId,Author author);
        ;
        void deleteAuthor(Long authorId);

        Author findAuthorById(Long authorId);

    }

