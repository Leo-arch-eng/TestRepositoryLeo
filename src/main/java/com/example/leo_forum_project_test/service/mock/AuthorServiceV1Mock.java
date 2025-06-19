package com.example.leo_forum_project_test.service.mock;

import com.example.leo_forum_project_test.entity.Author;
import com.example.leo_forum_project_test.mapper.AuthorMapper;
import com.example.leo_forum_project_test.repository.AuthorRepositoryV2;
import com.example.leo_forum_project_test.service.AuthorServiceV1;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
@Service
public class AuthorServiceV1Mock implements AuthorServiceV1 {
    private static final Logger logger =
            LoggerFactory.getLogger(AuthorServiceV1Mock.class);

    private final AuthorRepositoryV2 authorRepositoryV2;
    private AuthorMapper authorMapper;

    public AuthorServiceV1Mock(AuthorRepositoryV2 authorRepositoryV2) {
        this.authorRepositoryV2 = authorRepositoryV2;
    }

    @Override
    public Author createAuthor(@Valid Author author) {
        logger.info("Создание нового пользователя: {}", author);
        Author savedAuthor = authorRepositoryV2.save(author);
        logger.info("Создание нового пользователя прошло успешно: {}", savedAuthor.getName());
        return savedAuthor;
    }

    @Override
    public Author updateAuthor(Long authorId, @Valid Author author) {
        logger.info("Поиск автора по ID: {} и обновление данных", authorId);
        Optional<Author> optionalAuthor = authorRepositoryV2.findById(authorId);
        if (optionalAuthor.isPresent()) {
            Author existingAuthor = optionalAuthor.get();
            existingAuthor.setName(author.getName());
            existingAuthor.setSurname(author.getSurname());
            existingAuthor.setEmail(author.getEmail());
            existingAuthor.setAge(author.getAge());
            Author updatedAuthor = authorRepositoryV2.save(existingAuthor);
            logger.info("Обновление данных автора прошло успешно: {}", updatedAuthor);
            return updatedAuthor;
        } else {
            logger.warn("Автор с ID: {} не найден", authorId);
            return null;
        }
    }

    @Override
    public void deleteAuthor(Long authorId) {
        logger.info("Удаление автора по ID: {}", authorId);
        try {
            authorRepositoryV2.deleteById(authorId);
            logger.info("Удаление автора с ID: {} прошло успешно!", authorId);
        } catch (Exception e) {
            logger.error("Ошибка при удалении автора с ID: {}: {}", authorId, e.getMessage());
        }
    }

    @Override
    public Author findAuthorById(Long authorId) {
        logger.info("Поиск автора по ID: {}", authorId);
        Optional<Author> optionalAuthor = authorRepositoryV2.findById(authorId);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            logger.info("Автор с ID: {} найден", authorId);
            return author;
        } else {
            logger.warn("Автор с ID: {} не найден", authorId);
            return null;
        }
    }
}