package com.example.leo_forum_project_test.controller;

import com.example.leo_forum_project_test.dto.CommentDto;
import com.example.leo_forum_project_test.service.CommentServiceV1;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/topic/message/comment")
public class CommentControllerV1 {

    private final CommentServiceV1 commentServiceV1;

    @Autowired
    public CommentControllerV1(CommentServiceV1 commentServiceV1) {
        this.commentServiceV1 = commentServiceV1;
    }

    @GetMapping("/{comment_id}")
    public CommentDto getCommentById(
            @PathVariable("comment_id")
            @Min(0)
            @Max(100) Long commentId
    ) {
        return commentServiceV1.getCommentById(commentId);
    }

    @PostMapping
    public CommentDto createComment(
            @Valid
            @RequestBody CommentDto commentDto
    ) {
        return commentServiceV1.createComment(commentDto);
    }

    @PutMapping("/{comment_id}")
    public CommentDto updateCommentById(
            @PathVariable("comment_id")
            @Min(0)
            @Max(100) Long commentId,
            @Valid
            @RequestBody CommentDto commentDto
    ) {
        return commentServiceV1.updateCommentById(commentId, commentDto);
    }

    @DeleteMapping("/{comment_id}")
    public void deleteCommentById(
            @PathVariable("comment_id")
            @Min(0)
            @Max(100) Long commentId
    ) {
        commentServiceV1.deleteCommentById(commentId);
    }
}