package com.example.leo_forum_project_test.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "author_id")
    private long author_id;

    @Column(name = "message_text")
    private String message_text;

    @Column(name = "message_date")
    private int message_date;

}
