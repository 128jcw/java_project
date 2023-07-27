package com.example.project5.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project5.dto.RecruitPostReplyReadDto;
import com.example.project5.dto.RecruitPostReplyRegisterDto;
import com.example.project5.dto.RecruitPostReplyUpdateDto;
import com.example.project5.service.RecruitPostReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recruitPostReply")
public class RecruitPostReplyRestController {
    
    private final RecruitPostReplyService recruitPostReplyService;
    
    // 댓글 작성 
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Integer> registerReply(@RequestBody RecruitPostReplyRegisterDto dto) {
        log.info("registerReply(dto={})", dto);
        
        Integer replyId=recruitPostReplyService.create(dto);
        
        return ResponseEntity.ok(replyId);
    }
    
    public ResponseEntity<Integer> childRegisterReply(@RequestBody RecruitPostReplyRegisterDto dto){
        Integer replyId = recruitPostReplyService.create(dto);
        return null;
        
    }
    
    // 게시글 댓글 불러오기
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all/{postId}")
    public ResponseEntity<List<RecruitPostReplyReadDto>> readAllReplies(@PathVariable Integer postId) {
        log.info("readAllReplies(postId={})", postId);
        
        List<RecruitPostReplyReadDto> list = recruitPostReplyService.readReplies(postId);
        log.info("# of list = ", list.size());
        
        return ResponseEntity.ok(list);
    }
    
    // 수정
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{replyId}")
    public ResponseEntity<RecruitPostReplyReadDto> getReply(@PathVariable Integer replyId) {
        log.info("getReply(replyId={})", replyId);
        
        RecruitPostReplyReadDto dto = recruitPostReplyService.readReply(replyId);
        
        return ResponseEntity.ok(dto);
    }
    
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{replyId}")
    public ResponseEntity<Integer> deleteReply(@PathVariable Integer replyId) {
        log.info("deleteReply(replyId={})", replyId);
        
        Integer result = recruitPostReplyService.delete(replyId);
        
        return ResponseEntity.ok(result);
    }
    
    // 댓글 수정
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{replyId}")
    public ResponseEntity<Integer> updateReply(@PathVariable Integer replyId,
            @RequestBody RecruitPostReplyUpdateDto dto) {
        log.info("updateReply(replyId={}, dto={})", replyId, dto);
        
        dto.setReplyId(replyId);
        Integer result = recruitPostReplyService.updateDto(dto);
        
        return ResponseEntity.ok(result);
    }
    
}
