package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.entity.MessageE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepositoryV2 extends JpaRepository<MessageE, Long> {
}
