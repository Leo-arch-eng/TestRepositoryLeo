package com.example.leo_forum_project_test.controller;


import com.example.leo_forum_project_test.dto.Author;
import com.example.leo_forum_project_test.service.AuthorServiceV1;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("api/v1/topic/author")
public class AuthorControllerV1 {

    @Autowired
    private AuthorServiceV1 authorServiceV1;

    public AuthorControllerV1(AuthorServiceV1 authorServiceV1) {
        this.authorServiceV1 = authorServiceV1;
    }

    @GetMapping("/{author_id}")
    public Author getAuthorById(
            @PathVariable("author_id")
            @Min(0)
            @Max(100) Long author_id
    ) {
        Author author = authorServiceV1.findAuthorById(author_id);
        return author;
    }

    @PostMapping
    public Author createAuthor(
            @Valid
            @RequestBody Author author
    ) {
        Author createdAuthor = authorServiceV1.createAuthor(author);
        return createdAuthor;
    }

    @PutMapping("/{author_id}")
    public Author updateAuthor(
            @Valid
            @Min(0)
            @Max(100)
            @PathVariable(name = "author_id")Long author_id,
            @RequestBody Author author
    ) {
        Author updatedAuthor = authorServiceV1.updateAuthor(author_id, author);
        return updatedAuthor;
    }

    @DeleteMapping("/{author_id}")
    public void deleteAuthor(
            @Min(0)
            @Max(100)
            @PathVariable("author_id") Long author_id) {
        authorServiceV1.deleteAuthor(author_id);
    }
}
