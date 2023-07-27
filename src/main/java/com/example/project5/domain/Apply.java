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
@ToString
@Entity(name = "APPLY")
@SequenceGenerator(name="APPLY_SEQ_GEN", sequenceName = "APPLY_SEQ" , allocationSize = 1)
public class Apply extends BaseTimeEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLY_SEQ_GEN")
    // 신청 번호 - PK
    private Integer id;

    @Column(nullable = false)
    private String joinNickname;
    
    // 다대일관계, 신청이 들어갈 달릴 포스트 Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    private RecruitPost recruitPost;

}
