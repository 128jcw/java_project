package com.example.project5.dto;

import java.time.LocalDateTime;

import com.example.project5.domain.RecruitPostReply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class RecruitPostReplyReadDto {
    
    private Integer replyId;
    private Integer recruitPostId;
    private String replyText;
    private String writer;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    
    public static RecruitPostReplyReadDto fromEntity(RecruitPostReply entity) {
        return RecruitPostReplyReadDto.builder()
                .replyId(entity.getId())
                .recruitPostId(entity.getRecruitPost().getId())
                .replyText(entity.getReplyText())
                .writer(entity.getWriter())
                .createdTime(entity.getCreatedTime())
                .modifiedTime(entity.getModifidTime())
                .build();
    }
    
}
