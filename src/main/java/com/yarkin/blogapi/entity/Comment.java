package com.yarkin.blogapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Comment {
    private long id;
    private String text;
    private String creationDate;
    private long postId;
}
