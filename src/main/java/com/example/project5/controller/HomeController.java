package com.example.project5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.project5.domain.RecruitPost;
import com.example.project5.service.RecruitPostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {
    
    private final RecruitPostService recruitPostService;
    
    @GetMapping("/")
    public String home(Model model) {
        log.info("home()");
        
        RecruitPost bestLikecountPost=recruitPostService.bestLikecount(0);
        RecruitPost bestViewcountPost=recruitPostService.bestViewcount();
        
        if (bestLikecountPost!=null) {
            model.addAttribute("bestLikecountPost", bestLikecountPost);
        }
        
        if (bestViewcountPost!=null) {
            model.addAttribute("bestViewcountPost", bestViewcountPost);
        }
        
        return "/home";
    }
}
