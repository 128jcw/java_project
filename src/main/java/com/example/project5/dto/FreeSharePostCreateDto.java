package com.example.project5.dto;

import java.time.LocalDateTime;

import com.example.project5.domain.FreeSharePost;

import lombok.Data;

@Data
public class FreeSharePostCreateDto {

    private String title;
    private String content;
    private String author;
    private String price;
    private String transaction;//'direct, parcel, 둘다 선택' 3가지 중 하나의 문자열로 저장!
     
    public FreeSharePost toEntity() {
        return FreeSharePost.builder().title(title).content(content).author(author).price(price).transaction(transaction).build();
    }
}
