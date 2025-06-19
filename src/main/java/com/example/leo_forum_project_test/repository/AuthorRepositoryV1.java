package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.dto.Author;

public interface AuthorRepositoryV1 {
    Author createAuthor(Author author);

    Author updateAuthor(Long authorId,Author author);
            ;
    void deleteAuthor(Long authorId);

    Author findAuthorById(Long authorId);
}
