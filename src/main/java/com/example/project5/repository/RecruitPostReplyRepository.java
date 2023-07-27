package com.example.project5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project5.domain.RecruitPostReply;

public interface RecruitPostReplyRepository extends JpaRepository<RecruitPostReply, Integer> {
    
    List<RecruitPostReply> findByRecruitPostIdOrderByIdDesc(Integer recruitPostId);
    
    List<RecruitPostReply> findByRecruitPostIdOrderByCreatedTime(Integer recruitPostId);
    
    List<RecruitPostReply> findByWriter(String writer);
}
