package com.example.project5.dto;

import lombok.Data;

@Data
public class ReviewPostUpdateDto {
    
    private Integer id;
    private String title;
    private String content;
    private String author;
    private String organizer;
    private String activity;
    
}
