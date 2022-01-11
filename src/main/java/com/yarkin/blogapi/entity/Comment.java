package com.yarkin.blogapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Comment {
    private long id;
    private String text;
    private LocalDateTime creationDate;
    private long postId;
}
