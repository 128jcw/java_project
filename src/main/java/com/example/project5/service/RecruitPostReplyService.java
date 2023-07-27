package com.example.project5.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project5.domain.RecruitPost;
import com.example.project5.domain.RecruitPostReply;
import com.example.project5.dto.RecruitPostReplyReadDto;
import com.example.project5.dto.RecruitPostReplyRegisterDto;
import com.example.project5.dto.RecruitPostReplyUpdateDto;
import com.example.project5.repository.RecruitPostReplyRepository;
import com.example.project5.repository.RecruitPostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecruitPostReplyService {
    
    private final RecruitPostReplyRepository recruitPostReplyRepository;
    private final RecruitPostRepository recruitPostRepository;
    
    public Integer create(RecruitPostReplyRegisterDto dto) {
        log.info("create(dto={})", dto);
        
        RecruitPost recruitPost=recruitPostRepository.findById(dto.getPostId()).get();
        
        RecruitPostReply reply=RecruitPostReply.builder()
                .recruitPost(recruitPost).replyText(dto.getReplyText()).writer(dto.getWriter())
                .build();
        
        reply=recruitPostReplyRepository.save(reply);
        
        return reply.getId();
    }
    
    @Transactional(readOnly=true)
    public List<RecruitPostReplyReadDto> readReplies(Integer postId) {
        log.info("readReplies(postId={})", postId);
        
        List<RecruitPostReply> list=recruitPostReplyRepository.findByRecruitPostIdOrderByCreatedTime(postId);
        
        return list.stream()
                .map(RecruitPostReplyReadDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public RecruitPostReplyReadDto readReply(Integer replyId) {
        log.info("readReply(replyId={})", replyId);
        
        RecruitPostReply entity = recruitPostReplyRepository.findById(replyId).get();
        
        return RecruitPostReplyReadDto.fromEntity(entity);
    }
    
    public Integer delete(Integer replyId) {
        log.info("delete(replyId={})", replyId);
        
        recruitPostReplyRepository.deleteById(replyId);
        
        return replyId;
    }
    
    public void deleteByRecruitPostId(Integer recruitPostId) {
        log.info("delete(recruitPostId= {})", recruitPostId);
        
        List<RecruitPostReply> list = recruitPostReplyRepository.findByRecruitPostIdOrderByIdDesc(recruitPostId);
        
        for (RecruitPostReply r : list) {
            recruitPostReplyRepository.deleteById(r.getId());
        }
        
    }
    
    @Transactional
    public Integer updateDto(RecruitPostReplyUpdateDto dto) {
        log.info("update(dto={})", dto);
        
        RecruitPostReply entity = recruitPostReplyRepository.findById(dto.getReplyId()).get();
        
        entity.updateReply(dto.getReplyText());
        
        return entity.getId();
    }
    
    @Transactional
    public void deleteByWriter(String writer) {
        log.info("delete(writer={})", writer);
        
        List<RecruitPostReply> list=recruitPostReplyRepository.findByWriter(writer);
        
        for (RecruitPostReply a : list) {
            recruitPostReplyRepository.deleteById(a.getId());
        }
    }
    
}
