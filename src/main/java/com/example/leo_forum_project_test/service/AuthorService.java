package com.example.leo_forum_project_test.service;


import com.example.leo_forum_project_test.entity.Author;

import java.util.List;

public interface AuthorService {

    public List<Author> getAllAuthors();

    public Author getAuthorById(int id);

    public void addAuthor(Author author);

    public void updateAuthor(Author author);

    public void deleteAuthor(int id);
}
