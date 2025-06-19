package com.example.leo_forum_project_test.service;


import com.example.leo_forum_project_test.dao.AuthorDao;
import com.example.leo_forum_project_test.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    private AuthorDao authorDAO;

    @Override
    public List<Author> getAllAuthors() {

        return authorDAO.getAllAuthors();
    }

    @Override
    public Author getAuthorById(int id) {

        return authorDAO.getAuthorById(id);
    }

    @Override
    public void addAuthor(Author author) {
        authorDAO.addAuthor(author);

    }

    @Override
    public void updateAuthor(Author author) {

         authorDAO.updateAuthor(author);
    }

    @Override
    public void deleteAuthor(int id) {

        authorDAO.deleteAuthor(id);

    }
}
