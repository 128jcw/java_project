package com.example.project5.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project5.dto.ApplyJoinDto;
import com.example.project5.service.ApplyService;
import com.example.project5.service.RecruitPostService;

import groovyjarjarpicocli.CommandLine.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApplyController {
    
    private final ApplyService applyService;
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/apply")
    public ResponseEntity<Integer> joinPostApply(@RequestBody ApplyJoinDto dto) {
        log.info("joinPostApply(dto={})", dto);
        
        Integer applyId = applyService.JoinPost(dto);
        
        return ResponseEntity.ok(applyId);
    }
    
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/api/apply")
    public ResponseEntity<Integer> deleteApply(String joinNickname, Integer recruitPostId){
        log.info("deleteApply(joinNickname={}, recruitPostId={})", joinNickname, recruitPostId);
        
        int result = applyService.delete(joinNickname, recruitPostId);
        
        return ResponseEntity.ok(result);
    }
    
}
