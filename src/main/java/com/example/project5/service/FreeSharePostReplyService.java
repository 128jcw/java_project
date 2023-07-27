package com.example.project5.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project5.domain.FreeSharePost;
import com.example.project5.domain.FreeSharePostReply;
import com.example.project5.dto.FreeSharePostReplyReadDto;
import com.example.project5.dto.FreeSharePostReplyRegisterDto;
import com.example.project5.dto.FreeSharePostReplyUpdateDto;
import com.example.project5.repository.FreeSharePostReplyRepository;
import com.example.project5.repository.FreeSharePostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FreeSharePostReplyService {
    
    private final FreeSharePostReplyRepository freeSharePostReplyRepository;
    private final FreeSharePostRepository freeSharePostRepository;
    
    public Integer create(FreeSharePostReplyRegisterDto dto) {
        
        log.info("create(dto={})", dto);
        
        FreeSharePost freeSharePost=freeSharePostRepository.findById(dto.getPostId()).get();
        
        FreeSharePostReply reply=FreeSharePostReply.builder()
                .freeSharePost(freeSharePost).replyText(dto.getReplyText()).writer(dto.getWriter())
                .build();
        
        reply=freeSharePostReplyRepository.save(reply);
        
        return reply.getId();
    }
    
    @Transactional(readOnly = true)
    public List<FreeSharePostReplyReadDto> readReplies(Integer postId) {
        log.info("readReplies(postId={})", postId);
        
        List<FreeSharePostReply> list=freeSharePostReplyRepository.findByFreeSharePostIdOrderByCreatedTime(postId);
        
        return list.stream()
                .map(FreeSharePostReplyReadDto::fromEntity)
                .collect(Collectors.toList());
        
    }
    
    @Transactional(readOnly = true)
    public FreeSharePostReplyReadDto readReply(Integer replyId) {
        log.info("readReply(replyId={})", replyId);
        
        FreeSharePostReply entity = freeSharePostReplyRepository.findById(replyId).get();
        
        return FreeSharePostReplyReadDto.fromEntity(entity);
    }
    
    public Integer delete(Integer replyId) {
        log.info("delete(replyId={})", replyId);
        
        freeSharePostReplyRepository.deleteById(replyId);
        
        return replyId;
    }
    
    public void deleteByFreeSharePostId(Integer freeSharePostId) {
        log.info("delete(freeSharePostId");
        
        List<FreeSharePostReply> list=freeSharePostReplyRepository.findByFreeSharePostIdOrderByIdDesc(freeSharePostId);
        for (FreeSharePostReply a : list) {
            freeSharePostReplyRepository.deleteById(a.getId());
        }
    }
    
    @Transactional
    public Integer updateDto(FreeSharePostReplyUpdateDto dto) {
        log.info("update(dto={})", dto);
        
        FreeSharePostReply entity=freeSharePostReplyRepository.findById(dto.getReplyId()).get();
        
        entity.updateReply(dto.getReplyText());
        
        return entity.getId();
    }
    
    @Transactional
    public void deleteByWriter(String writer) {
        log.info("delete(writer={})", writer);
        
        List<FreeSharePostReply> list = freeSharePostReplyRepository.findByWriter(writer);
        for (FreeSharePostReply a : list) {
            freeSharePostReplyRepository.deleteById(a.getId());
        }
    }
    
}