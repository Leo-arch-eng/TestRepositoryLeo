package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.dto.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepositoryV2 extends JpaRepository<Comment, Long> {
}
