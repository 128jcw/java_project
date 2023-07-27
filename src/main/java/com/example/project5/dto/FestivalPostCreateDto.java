package com.example.project5.dto;

import java.time.LocalDateTime;

import com.example.project5.domain.FestivalPost;

import lombok.Data;

@Data
public class FestivalPostCreateDto {

    private String title;
    private String content;
    private String author;
    private String festivalArea;
    private String festivalPeriod;
    private String festivalCharacter;
    private String festivalInfo;
    private String festivalPlace;
    private String festivalPrice;
    private String festivalAgency;
    private String festivalInquiry;
    
   

    public FestivalPost toEntity() {
        return FestivalPost.builder()
                .title(title).content(content)
                .festivalAgency(festivalAgency).festivalArea(festivalArea)
                .festivalCharacter(festivalCharacter).festivalInfo(festivalInfo).festivalPlace(festivalPlace)
                .festivalInquiry(festivalInquiry).festivalPeriod(festivalPeriod).festivalPrice(festivalPrice)
                .build();
    }

}