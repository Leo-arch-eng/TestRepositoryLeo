package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.entity.TopicE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepositoryV2 extends JpaRepository<TopicE, Long> {
}
