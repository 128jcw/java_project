package com.example.project5.dto;

import java.time.LocalDateTime;

import com.example.project5.domain.FreeSharePostReply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class FreeSharePostReplyReadDto {
    
    private Integer replyId;
    private Integer freeSharePostId;
    private String replyText;
    private String writer;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    
    public static FreeSharePostReplyReadDto fromEntity(FreeSharePostReply entity) {
        return FreeSharePostReplyReadDto.builder()
                .replyId(entity.getId())
                .freeSharePostId(entity.getFreeSharePost().getId())
                .replyText(entity.getReplyText())
                .writer(entity.getWriter())
                .createdTime(entity.getCreatedTime())
                .modifiedTime(entity.getModifidTime())
                .build();
    }
}
