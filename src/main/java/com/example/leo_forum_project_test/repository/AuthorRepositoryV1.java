package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.entity.AuthorE;

public interface AuthorRepositoryV1 {
    AuthorE createAuthor(AuthorE authorE);

    AuthorE updateAuthor(Long authorId, AuthorE authorE);
    void deleteAuthor(Long authorId);

    AuthorE findAuthorById(Long authorId);
}
