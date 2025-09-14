package com.example.leo_forum_project_test.repository.mock;

import com.example.leo_forum_project_test.entity.AuthorE;
import com.example.leo_forum_project_test.repository.AuthorRepositoryV1;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AuthorRepositoryV1Mock implements AuthorRepositoryV1 {

    private final Map<Long, AuthorE> authorMap;
    private static Long lastCreatedAuthorId = 0L;

    public AuthorRepositoryV1Mock() {
        this.authorMap = new HashMap<Long, AuthorE>();
    }

    @Override
    public AuthorE createAuthor(AuthorE authorE) {
        Long newId = lastCreatedAuthorId++;
        authorE.setId(newId);
        authorMap.put(newId, authorE);
        System.out.println("Добавлен новый автор с идентификатором: "
                + lastCreatedAuthorId + " = " + authorE.getName() + ", " + authorE.getSurname() + ", " +
                authorE.getEmail());
        return authorE;
    }

    @Override
    public AuthorE updateAuthor(Long authorId, AuthorE authorE) {
        if(authorMap.containsKey(authorId)){
            authorMap.put(authorId, authorE);
            System.out.println("Автор с ID " + authorId + "был обновлен");
        }

        return authorE;
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
    public AuthorE findAuthorById(Long authorId) {
        AuthorE authorE = authorMap.get(authorId);
        return authorE;
    }
}
