package com.example.project5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project5.domain.FreeSharePostReply;

public interface FreeSharePostReplyRepository extends JpaRepository<FreeSharePostReply, Integer> {
    
    List<FreeSharePostReply> findByFreeSharePostIdOrderByIdDesc(Integer freeSharePostId);
    
    List<FreeSharePostReply> findByFreeSharePostIdOrderByCreatedTime(Integer freeSharePostId);

    List<FreeSharePostReply> findByWriter(String writer);
    
}
