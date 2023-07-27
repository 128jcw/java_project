package com.example.project5.dto;

import com.example.project5.domain.FreeSharePostReply;

import lombok.Data;

@Data
public class FreeSharePostReplyUpdateDto {
    
    private Integer replyId;
    private String replyText;
    
    public FreeSharePostReply toEntity() {
        return FreeSharePostReply.builder()
                .id(replyId).replyText(replyText)
                .build();
    }
}
