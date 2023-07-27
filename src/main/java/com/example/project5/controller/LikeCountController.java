package com.example.project5.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.project5.dto.LikeCountDto;
import com.example.project5.service.LikeCountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LikeCountController {

    private final LikeCountService likeCountService;
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/likecount")
    public ResponseEntity<Integer> likecount(@RequestBody LikeCountDto dto){
        log.info("likecount(dto={}) controller", dto);
        
        Integer likecountId = likeCountService.insertLikecount(dto);
        
        return ResponseEntity.ok(likecountId);
        
    }
    
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/api/likecount")
    public ResponseEntity<Integer> deleteLike(String username, Integer recruitPostId){
        
        Integer result = likeCountService.deleteLikecount(username,recruitPostId);
        
        return ResponseEntity.ok(result);
        
    }
    
    
    
}
 