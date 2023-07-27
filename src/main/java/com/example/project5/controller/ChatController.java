package com.example.project5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChatController {
    
    
    @RequestMapping("/chat/chatPop")
    public String popList() {
        log.info("chat/popList()");
        
        
        return "/chat/chatList";
    }
    
    
    @PostMapping("/chat/chatRoom")
    public String createRoom(@RequestParam String name, RedirectAttributes rttr) {
        log.info("createRoom(name={})", name);
        
//        rttr.addFlashAttribute("roomName");
        return "redirect:/chat/chatRoom";
    }

}
