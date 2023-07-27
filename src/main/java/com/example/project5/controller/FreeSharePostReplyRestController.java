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
import org.springframework.web.bind.annotation.RestController;

import com.example.project5.dto.FreeSharePostReplyReadDto;
import com.example.project5.dto.FreeSharePostReplyRegisterDto;
import com.example.project5.dto.FreeSharePostReplyUpdateDto;
import com.example.project5.service.FreeSharePostReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/freesharePostReply")
public class FreeSharePostReplyRestController {
    
    private final FreeSharePostReplyService freeSharePostReplyService;
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Integer> registerReply(@RequestBody FreeSharePostReplyRegisterDto dto) {
        log.info("registerReply(dto={})", dto);
        
        Integer replyId=freeSharePostReplyService.create(dto);
        
        return ResponseEntity.ok(replyId);
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all/{postId}")
    public ResponseEntity<List<FreeSharePostReplyReadDto>> readAllReplies(@PathVariable Integer postId) {
        log.info("readAllReplies(postId={})", postId);
        
        List<FreeSharePostReplyReadDto> list = freeSharePostReplyService.readReplies(postId);
        
        log.info("# of list = ", list.size());
        
        return ResponseEntity.ok(list);
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{replyId}")
    public ResponseEntity<FreeSharePostReplyReadDto> getReply(@PathVariable Integer replyId) {
        log.info("getReply(replyId={})", replyId);
        
        FreeSharePostReplyReadDto dto=freeSharePostReplyService.readReply(replyId);
        
        return ResponseEntity.ok(dto);
    }
    
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{replyId}")
    public ResponseEntity<Integer> deleteReply(@PathVariable Integer replyId) {
        log.info("deleteReply(replyId={})", replyId);
        
        Integer result=freeSharePostReplyService.delete(replyId);
        
        return ResponseEntity.ok(result);
        
    }
    
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{replyId}")
    public ResponseEntity<Integer> updateReply(@PathVariable Integer replyId, 
            @RequestBody FreeSharePostReplyUpdateDto dto) {
        log.info("updateReply(replyId={}, dto={})", replyId, dto);
        
        dto.setReplyId(replyId);
        Integer result=freeSharePostReplyService.updateDto(dto);
        
        return ResponseEntity.ok(result);
    }
}