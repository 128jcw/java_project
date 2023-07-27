package com.example.project5.dto;

import com.example.project5.domain.RecruitPostReply;

import lombok.Data;

@Data
public class RecruitPostReplyUpdateDto {
    
    private Integer replyId;
    private String replyText;
    
    public RecruitPostReply toEntity() {
        return RecruitPostReply.builder()
                .id(replyId).replyText(replyText)
                .build();
    }
    
}
