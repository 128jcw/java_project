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
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "LIKECOUNT")
@Builder
@SequenceGenerator(name = "LIEKCOUNT_SEQ_GEN", sequenceName = "LIKECOUNT_SEQ", allocationSize = 1)
public class LikeCount {
    
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIEKCOUNT_SEQ_GEN")
  private Integer id;
  
  @Column(nullable = false)
  private String username;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private RecruitPost recruitPost;
  
}
