package com.example.project5.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project5.domain.Apply;
import com.example.project5.domain.LikeCount;
import com.example.project5.domain.RecruitPost;
import com.example.project5.dto.RecruitPostCreateDto;
import com.example.project5.dto.RecruitPostUpdateDto;
import com.example.project5.service.ApplyService;
import com.example.project5.service.LikeCountService;
import com.example.project5.service.RecruitPostReplyService;
import com.example.project5.service.RecruitPostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RecruitPostController {

    private final RecruitPostService recruitPostService;
    private final RecruitPostReplyService recruitPostReplyService;
    private final ApplyService applyService;
    private final LikeCountService likeCountService;

    @GetMapping("/post/list")
    public String list(Model model , @RequestParam(value = "page" , defaultValue = "0") int page) {
        log.info("list");
        
//        List<RecruitPost> list = recruitPostService.read();
//        model.addAttribute("list", list);
        Page<RecruitPost> paging = this.recruitPostService.getPostList(page);
        
        model.addAttribute("paging", paging);

        return "/post/list";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/post/create")
    public String post() {
        log.info("post()");

        return "/post/create";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/post/create")
    public String create(RecruitPostCreateDto dto, RedirectAttributes attrs,
            @RequestParam("imgFile") MultipartFile fileName) throws IOException {
        log.info("create(dto={}, fileName={})", dto ,fileName);

        RecruitPost entity = recruitPostService.create(dto, fileName);

        attrs.addFlashAttribute("createId", entity.getId());

        return "redirect:/post/list";
    } 

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/post/detail")
    public void detail(Integer id, Model model, Principal principal) {
        String username = principal.getName();
        String exist = "no";
        String isFull = "no";
        String closeDateEnd = "no";
        String likecheck = "no";
        String appliers="";
        log.info("detail(postId={}, username={})", id, username);

        RecruitPost post = recruitPostService.read(id);

        List<Apply> applyList = applyService.findByRecruitPostId(id);
        Integer countMember = applyList.size();
        
        LocalDateTime now = LocalDateTime.now();
        
        recruitPostService.updateView(id);
        
        if (countMember >= post.getTotalMember()) {
            isFull = "yes";
        }
        
        // now 가 closeDate보다 미래인지 검사 미래면 true 과거면 false
        if(now.isAfter( post.getCloseDate())) {
            closeDateEnd ="yes";
        }
            
        for (Apply a : applyList) {
            if (username.equals(a.getJoinNickname())) {
                exist = "yes";
                break;
            }
        }
        
        for (Apply a : applyList) {
            appliers=appliers+a.getJoinNickname()+"/";
        }
        
        List<LikeCount> likecountList = likeCountService.findByRecruitPostId(id);
        
        for (LikeCount l : likecountList) {
            if(username.equals(l.getUsername())){
                likecheck = "yes";
                break;
            }
        }
        
        
        model.addAttribute("likecheck",likecheck);
        model.addAttribute("post", post);
        model.addAttribute("countMember", countMember);
        model.addAttribute("exist", exist);
        model.addAttribute("closeDateEnd", closeDateEnd);
        model.addAttribute("isFull", isFull);
        model.addAttribute("appliers", appliers);

    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping( "/post/modify" )
    public void modify(Integer id, Model model, Principal principal) {
        String username = principal.getName();
        String exist = "no";
        String isFull = "no";
        String closeDateEnd = "no";
        log.info("detail(postId={}, username={})", id, username);

        RecruitPost post = recruitPostService.read(id);

        List<Apply> applyList = applyService.findByRecruitPostId(id);
        Integer countMember = applyList.size();
        
        LocalDateTime now = LocalDateTime.now();
        
        if (countMember >= post.getTotalMember()) {
            isFull = "yes";
        }
        
        // now 가 closeDate보다 미래인지 검사 미래면 true 과거면 false
        if(now.isAfter( post.getCloseDate())) {
            closeDateEnd ="yes";
        }
            
        for (Apply a : applyList) {
            if (username.equals(a.getJoinNickname())) {
                exist = "yes";
                break;
            }
        }

        model.addAttribute("post", post);
        model.addAttribute("countMember", countMember);
        model.addAttribute("exist", exist);
        model.addAttribute("closeDateEnd", closeDateEnd);
        model.addAttribute("isFull", isFull);

    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/post/delete")
    public String delete(Integer id, RedirectAttributes attrs) {
        log.info("delete(id={})", id);
        recruitPostReplyService.deleteByRecruitPostId(id);
        
        applyService.deleteByRecruitPostId(id);
        
        likeCountService.deleteByRecruitPostId(id);

        Integer postId = recruitPostService.delete(id);

        attrs.addFlashAttribute("deletePostId", postId);

        return "redirect:/post/list";
    }

    @PostMapping("/post/update")
    public String update(RecruitPostUpdateDto dto) throws Exception {

        Integer postId = recruitPostService.update(dto);
        log.info("postId={}",postId);

        return "redirect:/post/detail?id=" + dto.getId();
    }
    
    @PostMapping("/post/updateImg")
    public String updateImg(RecruitPostUpdateDto dto, 
            @RequestParam("imgFile") MultipartFile fileName) throws Exception {
        
        Integer postId = recruitPostService.updateImg(dto, fileName);
        
        
        log.info("postId={}",postId);
        
        return "redirect:/post/detail?id=" + dto.getId();
    }

    @GetMapping("/post/search")
    public String search(String type, String keyword, Model model) {
        log.info("search(type={}, keyword={}", type, keyword);

        List<RecruitPost> list = recruitPostService.search(type, keyword);
        model.addAttribute("post", list);
        log.info("post=" + list);
        return "/post/search";
    }

    @GetMapping("/map/main")
    public String mapAddress(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<RecruitPost> paging = this.recruitPostService.getMapList(page);
        model.addAttribute("paging", paging);
        return "/map/main";
    }

}
