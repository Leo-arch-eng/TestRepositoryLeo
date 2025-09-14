package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.entity.AuthorE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepositoryV2 extends JpaRepository<AuthorE, Long> {
}
