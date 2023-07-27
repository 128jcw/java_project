package com.example.project5.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikeCountDto {
    
    private String username;
    private Integer postId;
    
}
