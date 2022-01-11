package com.yarkin.blogapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Table;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Table(appliesTo = "posts")
public class Post {
    private long id;
    private String title;
    private String content;
    private LocalDateTime creationDate;
    private boolean star;
}
