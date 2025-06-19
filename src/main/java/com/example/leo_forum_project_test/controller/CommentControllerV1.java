package com.example.leo_forum_project_test.controller;


import com.example.leo_forum_project_test.dto.Comment;
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

    @Autowired
    private CommentServiceV1 commentServiceV1;

    public CommentControllerV1(CommentServiceV1 commentServiceV1) {
        this.commentServiceV1 = commentServiceV1;
    }

    @GetMapping("/{comment_id}")
    public Comment getCommentById(
            @PathVariable(name = "comment_id")
            @Min(0)
            @Max(100) Long commentId
    ) {
        Comment comment = commentServiceV1.getCommentById(commentId);
        return comment;
    }

    @PostMapping
    public Comment createComment(
            @Valid
            @RequestBody Comment comment
    ){
        Comment createdComment = commentServiceV1.createComment(comment);
        return createdComment;
    }

    @PutMapping("/{comment_id}")
    public Comment updateCommentById(
            @Valid
            @PathVariable(name = "comment_id")
            @Min(0)
            @Max(100) Long comment_id,
            @RequestBody Comment comment
    ){
        commentServiceV1.updateCommentById(comment_id,comment);
        return comment;
    }
    @DeleteMapping("/{comment_id}")
    public void deleteCommentById(
            @Min(0)
            @Max(100)
            @PathVariable(name = "comment_id") Long commentId) {
        commentServiceV1.deleteCommentById(commentId);
    }
}


