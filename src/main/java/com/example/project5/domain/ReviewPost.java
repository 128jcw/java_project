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
@Entity(name = "REVIEWPOSTS")
@SequenceGenerator(name="REVIEWPOSTS_SEQ_GEN", sequenceName = "REVIEWPOSTS_SEQ" ,allocationSize = 1)
public class ReviewPost extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEWPOSTS_SEQ_GEN")
    private Integer id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String content;
    
    @Column(nullable = false)
    private String author;
    
    @Column(nullable = false)
    private String organizer; // 모임 주최자
    
    @Column(nullable = false)
    private String activity; // 모임 이름
    
    
    public ReviewPost updateReviewPost(String title, String content, String author, 
            String organizer, String activity) {
        
        this.title=title;
        this.content=content;
        this.author=author;
        this.organizer=organizer;
        this.activity=activity;
        
        return this;
    }

}
