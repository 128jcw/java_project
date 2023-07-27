package com.example.project5.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.project5.domain.Apply;
import com.example.project5.domain.FreeSharePost;
import com.example.project5.domain.LikeCount;
import com.example.project5.domain.Member;
import com.example.project5.domain.RecruitPost;
import com.example.project5.dto.MemberUpdateDto;
import com.example.project5.service.ApplyService;
import com.example.project5.service.FreeSharePostReplyService;
import com.example.project5.service.FreeSharePostService;
import com.example.project5.service.LikeCountService;
import com.example.project5.service.MemberService;
import com.example.project5.service.RecruitPostReplyService;
import com.example.project5.service.RecruitPostService;
import com.example.project5.service.ReviewPostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyPageController {
    
    private final RecruitPostService recruitPostService;
    private final RecruitPostReplyService recruitPostReplyService;
    private final FreeSharePostService freeSharePostService;
    private final FreeSharePostReplyService freeSharePostReplyService;
    private final ReviewPostService reviewPostService;
    private final ApplyService applyService;
    private final MemberService memberService;
    private final LikeCountService likeCountService;
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/member/mypage")
    public void initMypage(Principal principal, Model model) {
        String host=principal.getName();
        log.info("initMypage(host={})", host);

        List<RecruitPost> recruitPostList = recruitPostService.readByAuthor(host);
        List<FreeSharePost> freeSharePostList = freeSharePostService.readByAuthor(host);
        List<Apply> applyList = applyService.findByUsername(host);
        List<RecruitPost> applyRecruitPostList = new ArrayList<>();
        for (Apply a : applyList) {
            RecruitPost r = recruitPostService.read(a.getRecruitPost().getId());
            applyRecruitPostList.add(r);
        }
        
        List<LikeCount> likecountList = likeCountService.findByUsername(host);
        List<RecruitPost> likecountPostList = new ArrayList<>();
        for(LikeCount l : likecountList) {
            RecruitPost r = recruitPostService.read(l.getRecruitPost().getId());
            likecountPostList.add(r);
        }
        
        model.addAttribute("recruitPostList", recruitPostList);
        model.addAttribute("freeSharePostList", freeSharePostList);
        model.addAttribute("applyList", applyList);
        model.addAttribute("applyRecruitPostList", applyRecruitPostList);
        model.addAttribute("likecountPostList",likecountPostList);
        
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/member/update")
    public String memberUpdate(Principal principal, Model model) {
        String host=principal.getName();
        log.info("update(id={})", host);
        
        Member member=memberService.findByUsername(host);
        
        model.addAttribute("member", member);
        
        return "/member/update";
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/member/update")
    public String memberModify(MemberUpdateDto dto) {
        log.info("memberModify()");
        
        memberService.update(dto);
        
        return "redirect:/member/mypage";
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/member/delete")
    public String memberDelete(Principal principal) {
        log.info("memberDelete()");
        String username=principal.getName();
        
        applyService.deleteByUsername(username);
        recruitPostReplyService.deleteByWriter(username);
        likeCountService.deleteByUsername(username);
        recruitPostService.deleteByAuthor(username);
        freeSharePostReplyService.deleteByWriter(username);
        freeSharePostService.deleteByAuthor(username);
        reviewPostService.deleteByAuthor(username);
        memberService.delete(username);
        
        return "redirect:/logout";
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/member/passwordUpdate")
    public String passwordUpdate() {
        log.info("passwordUpdate()");
        
        return "/member/passwordUpdate";
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/member/passwordUpdate")
    public String passwordUpdate(Principal principal, Model model, String passwordConfirm) {
        log.info("passwordUpdate()");
        // String newPassword=model.getAttribute("password").toString();
        String username=principal.getName();
        
        memberService.updatePassword(passwordConfirm, username);
        
        return "redirect:/logout";
    }
    
}
