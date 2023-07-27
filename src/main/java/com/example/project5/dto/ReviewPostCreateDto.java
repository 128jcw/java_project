package com.example.project5.dto;

import com.example.project5.domain.ReviewPost;

import lombok.Data;

@Data
public class ReviewPostCreateDto {
    
    private String title;
    private String content;
    private String author;
    private String organizer;
    private String activity;
    
    public ReviewPost toEntity() {
        return ReviewPost.builder().title(title).content(content)
                .author(author).organizer(organizer)
                .activity(activity).build();
    }
    
}
