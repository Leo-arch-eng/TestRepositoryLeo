package com.example.leo_forum_project_test.service;

import com.example.leo_forum_project_test.dto.CommentDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CommentServiceV1 {

    CommentDto createComment(@Valid CommentDto commentDto);

    CommentDto getCommentById(Long commentId);

    CommentDto updateCommentById(Long commentId, @Valid CommentDto commentDto);

    void deleteCommentById(Long commentId);

    List<CommentDto> findAllComments();
}