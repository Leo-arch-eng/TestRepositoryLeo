package com.example.leo_forum_project_test.repository.mock;

import com.example.leo_forum_project_test.dto.Author;
import com.example.leo_forum_project_test.repository.AuthorRepositoryV1;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AuthorRepositoryV1Mock implements AuthorRepositoryV1 {

    private final Map<Long, Author> authorMap;
    private Long lastCreatedAuthorId = 0L;

    public AuthorRepositoryV1Mock() {
        this.authorMap = new HashMap<Long, Author>();
    }

    @Override
    public Author createAuthor(Author author) {
        Long newId = lastCreatedAuthorId++;
        author.setAuthorId(newId);
        authorMap.put(newId, author);
        System.out.println("Добавлен новый автор с идентификатором: + "
                + lastCreatedAuthorId + " = " + author.getName() + ", " + author.getSurname() + ", " +
                author.getEmail());
        return author;
    }

    @Override
    public Author updateAuthor(Long authorId, Author author) {
        if(authorMap.containsKey(authorId)){
            authorMap.put(authorId, author);
            System.out.println("Автор с ID " + authorId + "был обновлен");
        }

        return author;
    }

    @Override
    public void deleteAuthor(Long authorId) {
        if (authorMap.containsKey(authorId)) {
            authorMap.remove(authorId);
            System.out.println("Был удален автор с ID: " + authorId);
        }else{
            System.out.println("Автор с ID: " + authorId + " не найден");
        }

    }

    @Override
    public Author findAuthorById(Long authorId) {
        Author author = authorMap.get(authorId);
        return author;
    }
}
