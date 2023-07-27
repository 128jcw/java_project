package com.example.project5.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Entity(name = "COMMUNITYS")

@SequenceGenerator(name = "COMMUNITYS_SEQ_GEN", sequenceName = "COMMUNITYS_SEQ", initialValue = 1, allocationSize = 1)

public class FestivalPost extends BaseTimeEntity {
    
    @Id // Primary key(고유키)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMUNITYS_SEQ_GEN")
    private Integer id;
    
    @Column(nullable = false) // Not Null 제약조건
    private String title;
    
    @Column(nullable = false , length = 1000)
    private String content;
    
    @Column(nullable = false)
    private String festivalArea;
    
    @Column(nullable = false)
    private String festivalPeriod;
    
    @Column(nullable = false)
    private String festivalCharacter;
    
    @Column(nullable = false)
    private String festivalInfo;
    
    @Column(nullable = false)
    private String festivalPlace;
    
    @Column(nullable = false)
    private String festivalPrice;
    
    @Column(nullable = false)
    private String festivalAgency;
    
    @Column(nullable = false)
    private String festivalInquiry;
    
    @Column
    private String filePath; // 업로드한 이미지 파일의 경로
    
    @Column
    private String fileName; // 파일 이름
    
    private final String postGroup="festivalPost"; // 여러개의 포스트들을 분류하는 제목
    
    public FestivalPost updateFestivalPost(String title, String content, String festivalArea, String festivalPeriod
            , String festivalCharacter, String festivalInfo, String festivalPlace, String festivalPrice, String festivalAgency,
            String festivalInquiry, String filePath, String fileName) {
        this.title=title;
        this.content=content;
        this.festivalArea=festivalArea;
        this.festivalAgency=festivalAgency;
        this.festivalCharacter=festivalCharacter;
        this.festivalInfo=festivalInfo;
        this.festivalInquiry=festivalInquiry;
        this.festivalPeriod=festivalPeriod;
        this.festivalPrice=festivalPrice;
        this.festivalPlace=festivalPlace;
        this.fileName=fileName;
        this.filePath=filePath;
        
        return this;
    }
    
    public FestivalPost updateFestivalPost(String title, String content, String festivalArea, String festivalPeriod
            , String festivalCharacter, String festivalInfo, String festivalPlace, String festivalPrice, String festivalAgency,
            String festivalInquiry) {
        this.title=title;
        this.content=content;
        this.festivalArea=festivalArea;
        this.festivalAgency=festivalAgency;
        this.festivalCharacter=festivalCharacter;
        this.festivalInfo=festivalInfo;
        this.festivalInquiry=festivalInquiry;
        this.festivalPeriod=festivalPeriod;
        this.festivalPrice=festivalPrice;
        this.festivalPlace=festivalPlace;
        
        return this;
    }
}
