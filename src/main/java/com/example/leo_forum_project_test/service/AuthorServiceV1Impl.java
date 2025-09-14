package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.AuthorDto;
import com.example.leo_forum_project_test.entity.AuthorE;
import com.example.leo_forum_project_test.mapper.AuthorMapper;
import com.example.leo_forum_project_test.repository.AuthorRepositoryV2;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
@RequiredArgsConstructor
@Service
public class AuthorServiceV1Impl implements AuthorServiceV1 {
    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceV1Impl.class);

    private final AuthorRepositoryV2 authorRepositoryV2;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorDto createAuthor(@Valid AuthorDto authorDto) {
        logger.info("Создание нового автора: {}", authorDto);
        AuthorE authorEEntity = authorMapper.toEntity(authorDto);
        AuthorE savedAuthorE = authorRepositoryV2.save(authorEEntity);
        return authorMapper.toDto(savedAuthorE);
    }

    @Override
    public AuthorDto updateAuthor(Long authorId, @Valid AuthorDto authorDto) {
        logger.info("Обновление автора по ID: {}", authorId);
        Optional<AuthorE> optionalAuthor = authorRepositoryV2.findById(authorId);
        if (optionalAuthor.isPresent()) {
            AuthorE existingAuthorE = optionalAuthor.get();
            // Обновляем поля
            existingAuthorE.setName(authorDto.getName());
            existingAuthorE.setSurname(authorDto.getSurname());
            existingAuthorE.setEmail(authorDto.getEmail());
            existingAuthorE.setAge(authorDto.getAge());
            AuthorE updatedAuthorE = authorRepositoryV2.save(existingAuthorE);
            return authorMapper.toDto(updatedAuthorE);
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
        } catch (Exception e) {
            logger.error("Ошибка при удалении автора: {}", e.getMessage());
        }
    }

    @Override
    public AuthorDto findAuthorById(Long authorId) {
        logger.info("Поиск автора по ID: {}", authorId);
        Optional<AuthorE> optionalAuthor = authorRepositoryV2.findById(authorId);
        return optionalAuthor.map(authorMapper::toDto).orElse(null);
    }
}