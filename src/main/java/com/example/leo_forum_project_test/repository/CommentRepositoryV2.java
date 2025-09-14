package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.entity.CommentE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepositoryV2 extends JpaRepository<CommentE, Long> {
}
