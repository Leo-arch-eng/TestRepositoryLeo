package com.example.leo_forum_project_test.dao;

import com.example.leo_forum_project_test.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AuthorDaoImpl implements AuthorDao {

    private SessionFactory sessionFactory;

    @Override
    public List<Author> getAllAuthors() {

        Session session = sessionFactory.getCurrentSession();
        List<Author> allAuthors = session.createQuery("from Author"
                ,Author.class).getResultList();

        return allAuthors;

    }
    @Override
    public Author getAuthorById(int id) {
        return null;
    }

    @Override
    public void addAuthor(Author author) {
    }

    @Override
    public void updateAuthor(Author author) {
    }

    @Override
    public void deleteAuthor(int id) {

    }
}
