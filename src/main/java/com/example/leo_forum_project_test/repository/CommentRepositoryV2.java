package com.example.leo_forum_project_test.repository;

import com.example.leo_forum_project_test.entity.CommentE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepositoryV2 extends JpaRepository<CommentE, Long> {

    @Query("SELECT c FROM CommentE c WHERE c.messageId = :messageId")
    List<CommentE> findMessageById(@Param("messageId") Long messageId);
}