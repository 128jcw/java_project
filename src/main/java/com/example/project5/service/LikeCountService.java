package com.example.project5.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.project5.domain.LikeCount;
import com.example.project5.domain.Member;
import com.example.project5.domain.RecruitPost;
import com.example.project5.dto.LikeCountDto;
import com.example.project5.repository.LikecountRepository;
import com.example.project5.repository.MemberRepository;
import com.example.project5.repository.RecruitPostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeCountService {
    private final RecruitPostRepository recruitPostRepository;
    private final LikecountRepository likeCountRepository;
    
    
    public List<LikeCount> findByRecruitPostId(Integer recruitPostId){
        return likeCountRepository.findByRecruitPostIdOrderByIdDesc(recruitPostId);
    }
    
    @Transactional  
    public Integer insertLikecount(LikeCountDto dto) {
        log.info("insertLikecount(dto= {})",dto);
        
        RecruitPost recruitPost = recruitPostRepository.findById(dto.getPostId()).get();
        
        LikeCount likecount = LikeCount.builder().recruitPost(recruitPost).username(dto.getUsername()).build();
        
        likeCountRepository.save(likecount);
        recruitPostRepository.plusLikeCount(dto.getPostId());
        
        return likecount.getId();
    }

    @Transactional
    public Integer deleteLikecount(String username, Integer recruitPostId) {
        
        likeCountRepository.deleteByRecruitPostId(username,recruitPostId);
        
        recruitPostRepository.minusLikeCount(recruitPostId);
        
        return recruitPostId;
    }
    
    public String checkMemberId(LikeCountDto dto) {
        
        Optional<LikeCount> result = likeCountRepository.findByUsernameAndRecruitPostId(dto.getUsername(), dto.getPostId());
        
        if(result.isPresent()) {
            return "nolike";
        } else {
            return "yeslike";
        }
    
    }

    public List<LikeCount> findByUsername(String username) {
        return likeCountRepository.findByUsernameOrderByIdDesc(username);
    }
    
    public void deleteByRecruitPostId(Integer recruitPostId) {
        List<LikeCount> list=likeCountRepository.findByRecruitPostIdOrderByIdDesc(recruitPostId);
        for (LikeCount l : list) {
            likeCountRepository.deleteById(l.getId());
        }
    }
    
    public void deleteByUsername(String username) {
        List<LikeCount> list=likeCountRepository.findByUsernameOrderByIdDesc(username);
        for (LikeCount l : list) {
            likeCountRepository.deleteById(l.getId());
        }
    }
}