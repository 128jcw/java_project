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
@ToString(exclude = {"freeSharePost"})
@Entity(name = "FREESHAREPOSTREPLIES")
@SequenceGenerator(name = "FREESHAREPOSTREPLIES_SEQ_GEN", sequenceName = "FREESHAREPOSTREPLIES_SEQ", allocationSize = 1)
public class FreeSharePostReply extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FREESHAREPOSTREPLIES_SEQ_GEN")
    private Integer id;
    
    @Column(nullable = false, length=1000)
    private String replyText;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private FreeSharePost freeSharePost;
    
    @Column(nullable = false)
    private String writer;
    
    public FreeSharePostReply updateReply(String replyText) {
        this.replyText=replyText;
        
        return this;
    }
    
}
