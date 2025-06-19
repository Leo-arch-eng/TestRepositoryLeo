package com.example.leo_forum_project_test.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "author_id")
    private long authorId;

    @Column(name = "message_id")
    private long messageId;

    @Column(name = "comment_text")
    private String comment_text;

    @Column(name = "comment_date")
    private int comment_date;

}
