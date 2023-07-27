package com.example.project5.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.project5.domain.Apply;
import com.example.project5.domain.RecruitPost;
import com.example.project5.dto.ApplyJoinDto;
import com.example.project5.repository.ApplyRepository;
import com.example.project5.repository.RecruitPostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyService {
    
    private final ApplyRepository applyrepository;
    private final RecruitPostRepository recruitPostRepository;
    
    public List<Apply> findByUsername(String username) {
        log.info("findByUsername(username={})", username);
        return applyrepository.findByJoinNicknameOrderByIdDesc(username);
    }
    
    public List<Apply> findByRecruitPostId(Integer recruitPostId) {
        log.info("findByRecruitPostId(recruitPostId={})", recruitPostId);
        return applyrepository.findByRecruitPostIdOrderByIdDesc(recruitPostId);
    }
    
    public Integer JoinPost(ApplyJoinDto dto) {
        log.info("joinPost(dto={})", dto);
        
        // 포스트 아이디 검색 
        RecruitPost recruitPost = recruitPostRepository.findById(dto.getPostId()).get();
        
        // DTO를 APPLY Entity로 변환
        Apply apply = Apply.builder().recruitPost(recruitPost).joinNickname(dto.getJoinNickname()).build();
        
        // 테이블에 저장
        applyrepository.save(apply);
        recruitPost.plusJoinMember(recruitPost.getJoinMember()+1);
        
        return apply.getId();
    }
    

    // 신청 삭제
    public int delete(String joinNickname, Integer recruitPostId) {
        log.info("delete(joinNickname={}, recruitPostId={})", joinNickname, recruitPostId);
        
        int result = applyrepository.deleteByjoinNickname(joinNickname, recruitPostId);
        
        return result;
    }

    public void deleteByRecruitPostId(Integer recruitPostId) {
        log.info("delete(recruitPostId={})", recruitPostId);
        
        List<Apply> list = applyrepository.findByRecruitPostIdOrderByIdDesc(recruitPostId);
        for (Apply a : list) {
            applyrepository.deleteById(a.getId());
        }
        
    }

    
    public String checkJoinNickname(String joinNickname, Integer recruitPostId) {
        
        log.info("checkJoinNickname(joinNickname={}, recruitPostId={})",joinNickname, recruitPostId);
        
        Optional<Apply> result = applyrepository.findByJoinNicknameAndRecruitPostId(joinNickname, recruitPostId);
        
        if(result.isPresent()) {
            return "nok";
        } else {
            return "ok";
        }

    }
    
    public void deleteByUsername(String username) {
        log.info("delete(username={})", username);
        
        List<Apply> list = applyrepository.findByJoinNicknameOrderByIdDesc(username);
        for (Apply a : list) {
            applyrepository.deleteById(a.getId());
        }
        
    }
}