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
@Getter
@Setter
@ToString
@Entity(name = "FREESHAREPOSTS")
@SequenceGenerator(name="FREESHAREPOSTS_SEQ_GEN", sequenceName = "FREESHAREPOSTS_SEQ" ,allocationSize = 1)
public class FreeSharePost extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FREESHAREPOSTS_SEQ_GEN")
    private Integer id;
    
    @Column(nullable = false)
    private String title;//작성할 때 포스트 제목
    
    @Column(nullable = false)
    private String content;//제품에 대한 설명
    
    @Column(nullable = false)
    private String author;//작성자
    
    @Column(nullable = false)
    private String price;//가격
        
    private String filePath;//이미지 경로 저장
    private String fileName;//이미지 이름 저장 
    
    @Column(nullable = false)       //direct방식과 parcel방식 중복도 가능..
    private String transaction;     //TODO CREATE.HTML에서 제품 거래방법(체크박스 중 무조건 하나를 선택하도록 해야함.)
    
    private final String postGroup="freeSharePost";
    
    public FreeSharePost updateFreeSharePost(String title, String content, String price) {
        this.title=title;
        this.content=content;
        //this.transaction=transaction;
        this.price=price;
        
        return this;
    }

}


