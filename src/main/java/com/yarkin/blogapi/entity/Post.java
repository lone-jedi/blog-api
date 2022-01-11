package com.yarkin.blogapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Post {
    private long id;
    private String title;
    private String content;
    private LocalDateTime creationDate;
}
