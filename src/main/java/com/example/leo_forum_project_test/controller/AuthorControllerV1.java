package com.example.leo_forum_project_test.controller;

import com.example.leo_forum_project_test.dto.AuthorDto;
import com.example.leo_forum_project_test.service.AuthorServiceV1;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("api/v1/topic/author")
public class AuthorControllerV1 {

    private final AuthorServiceV1 authorServiceV1;

    public AuthorControllerV1(AuthorServiceV1 authorServiceV1) {
        this.authorServiceV1 = authorServiceV1;
    }

    @GetMapping("/{author_id}")
    public AuthorDto getAuthorById(
            @PathVariable("author_id")
            @Min(0)
            @Max(100) Long author_id
    ) {
        return authorServiceV1.findAuthorById(author_id);
    }

    @PostMapping
    public AuthorDto createAuthor(
            @Valid
            @RequestBody AuthorDto authorDto
    ) {
        return authorServiceV1.createAuthor(authorDto);
    }

    @PutMapping("/{author_id}")
    public AuthorDto updateAuthor(
            @PathVariable(name = "author_id")
            @Min(0)
            @Max(100) Long author_id,
            @Valid
            @RequestBody AuthorDto authorDto
    ) {
        return authorServiceV1.updateAuthor(author_id, authorDto);
    }

    @DeleteMapping("/{author_id}")
    public void deleteAuthor(
            @PathVariable("author_id")
            @Min(0)
            @Max(100) Long author_id) {
        authorServiceV1.deleteAuthor(author_id);
    }
}