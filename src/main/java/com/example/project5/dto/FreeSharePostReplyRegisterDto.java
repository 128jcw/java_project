package com.example.project5.dto;

import lombok.Data;

@Data
public class FreeSharePostReplyRegisterDto {
    
    private Integer postId;
    private String replyText;
    private String writer;
}
