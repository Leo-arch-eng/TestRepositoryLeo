package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.dto.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepositoryV2 extends JpaRepository<Message, Long> {
}
