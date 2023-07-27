package com.example.project5.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project5.domain.ReviewPost;
import com.example.project5.dto.ReviewPostCreateDto;
import com.example.project5.dto.ReviewPostUpdateDto;
import com.example.project5.service.ReviewPostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ReviewPostController {
    
    private final ReviewPostService reviewPostService;
    
    
    @GetMapping("/reviewPost/list")
    public String list(Model model, @RequestParam(value = "page" , defaultValue = "0") int page) {
        log.info("list()");
        
//        List<ReviewPost> list=reviewPostService.read();
//        model.addAttribute("list", list);
        Page<ReviewPost> paging = this.reviewPostService.getPostList(page);
        
        model.addAttribute("paging",paging);
        
        
        return "/reviewPost/list";
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/reviewPost/create")
    public String postcreate() {
        log.info("postcreate()");
        return "/reviewPost/create";
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/reviewPost/create")
    public String create(ReviewPostCreateDto dto, RedirectAttributes attrs) {
        
        log.info("create(dto={})", dto);
        
        ReviewPost entity=reviewPostService.create(dto);
        
        attrs.addFlashAttribute("createdId", entity.getId());
        
        return "redirect:/reviewPost/list";
        
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/reviewPost/detail")
    public void detail(Integer id, Model model) {
        log.info("detail(id={})", id);
        
        ReviewPost post=reviewPostService.read(id);
        
        model.addAttribute("post", post);
        
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping( "/reviewPost/modify" )
    public void modify(Integer id, Model model, Principal principal) {
        String username=principal.getName();
        log.info("modify(id={}, username={})", id, username);
        
        ReviewPost post=reviewPostService.read(id);
        
        model.addAttribute("post", post);
        
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/reviewPost/delete")
    public String delete(Integer id, RedirectAttributes attrs) {
        log.info("delete(id={})", id);
        Integer postId=reviewPostService.delete(id);
        
        attrs.addFlashAttribute("deleteReviewPostId", postId);
        
        return "redirect:/reviewPost/list";
    }
    
    @PostMapping("/reviewPost/update")
    public String update(ReviewPostUpdateDto dto) {
        log.info("update(dto={})", dto);
        
        Integer postId=reviewPostService.update(dto);
        
        log.info("postId={}", postId);
        
        return "redirect:/reviewPost/detail?id=" + dto.getId();
        
    }
    
    @GetMapping("/reviewPost/search")
    public String search(String type, String keyword, Model model) {
    	log.info("search(type={}, keyword={}", type, keyword);
    	
    	List<ReviewPost> list = reviewPostService.search(type, keyword);
    	model.addAttribute("post",list);
    	
    	return "/reviewPost/search";
    }

}
