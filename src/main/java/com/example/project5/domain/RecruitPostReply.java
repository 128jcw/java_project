package com.example.project5.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = { "recruitPost" })
@Entity(name = "RECRUITPOSTREPLIES")
@SequenceGenerator(name = "RECRUITPOSTREPLIES_SEQ_GEN", sequenceName = "RECRUITPOSTREPLIES_SEQ", allocationSize = 1)
public class RecruitPostReply extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECRUITPOSTREPLIES_SEQ_GEN")
    private Integer id;
    
    @Column(nullable = false, length=1000)
    private String replyText;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private RecruitPost recruitPost;
    
    @Column(nullable = false)
    private String writer;
    
    public RecruitPostReply updateReply(String replyText) {
        this.replyText=replyText;
        
        return this;
    }
}
