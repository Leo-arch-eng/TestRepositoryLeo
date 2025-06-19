package com.example.leo_forum_project_test.dao;

import com.example.leo_forum_project_test.entity.Author;

import java.util.List;

public interface AuthorDao {

    public List<Author> getAllAuthors();

    public Author getAuthorById(int id);

    public void addAuthor(Author author);

    public void updateAuthor(Author author);

    public void deleteAuthor(int id);



}
