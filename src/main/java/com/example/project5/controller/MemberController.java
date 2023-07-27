package com.example.project5.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project5.dto.MemberRegisterDto;
import com.example.project5.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor 
@Controller
public class MemberController {
    
    private final MemberService memberService;
    
    @GetMapping("/member/signup")
    public void signUp() {
        log.info("signUp() GET");
    }
    
    @GetMapping("/member/checkid")
    @ResponseBody
    public ResponseEntity<String> checkUsername(String username) {
        log.info("checkUsername(username={})", username);
        
        String result=memberService.checkUsername(username);
        
        return ResponseEntity.ok(result);
        
    }
    
    @PostMapping("/member/signup")
    public String signUp(MemberRegisterDto dto) {
        log.info("signUp(dto = {})", dto);
        
        memberService.RegisterMember(dto);
        
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false)String error,
            @RequestParam(value = "exception", required = false)String exception,
            Model model) {
        log.info("login");
        
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        
        return "/login";
        
    }

}
