package com.example.project5.service;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.project5.domain.Apply;
import com.example.project5.domain.RecruitPost;
import com.example.project5.dto.JoinmemberCountDto;
import com.example.project5.dto.RecruitPostCreateDto;
import com.example.project5.dto.RecruitPostUpdateDto;
import com.example.project5.repository.RecruitPostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@RequiredArgsConstructor
@Service
public class RecruitPostService {

    private final RecruitPostRepository recruitPosrRepository;
    
    @Transactional(readOnly = true)
    public List<RecruitPost> read(){
        log.info("read()");
        return recruitPosrRepository.findByOrderByIdDesc();
    }
    
    @Transactional(readOnly = true)
    public List<RecruitPost> readByAuthor(String author) {
        log.info("readByAuthor(author={})", author);
        return recruitPosrRepository.findByAuthorOrderByIdDesc(author);
    }
    
    @Transactional(readOnly = true)
    public RecruitPost read(Integer id) {
        log.info("read(id={})", id);
        
        return recruitPosrRepository.findById(id).get();
    }

    public Integer delete(Integer id) {
        log.info("delete(id={})" , id);
        
        recruitPosrRepository.deleteById(id);
        
        return id;
    }
    
    
    public RecruitPost create(RecruitPostCreateDto dto, MultipartFile file) throws IOException {
        log.info("create(dto={})", dto);
        
//        // 파일 저장 경로 설정
//        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img"; 
//        
//        UUID uuid = UUID.randomUUID();
//        // 파일 고유 이름 랜덤 생성
//        String fileName = uuid + "_" + file.getOriginalFilename();
//        
//        // 파일 저장소
//        File saveFile = new File(filePath ,fileName); 
//        file.transferTo(saveFile); // throws Exception
//
//        //RecruitPost entity = recruitPosrRepository.save(dto.toEntity());
//        RecruitPost entity = dto.toEntity();
//        entity.setFileName(fileName);
//        entity.setFilePath("/img/" + fileName);
//      
        if(file.isEmpty()) {
            String fileSavePath = "/cardImg/homeLogo.gif";
            String fileStaticPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\cardImg";
            
            dto.setFileName("/cardImg/homeLogo.gif");
            dto.setFilePath(fileSavePath);
        } else {
            String fileSavePath = "/img/" + file.getOriginalFilename();
            String fileStaticPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
            
            File saveFile = new File(fileStaticPath, file.getOriginalFilename());
            file.transferTo(saveFile);
            
            dto.setFileName(file.getOriginalFilename());
            dto.setFilePath(fileSavePath);
        }
        
        RecruitPost recruitPost = dto.toEntity();
        recruitPost = recruitPosrRepository.save(recruitPost);
        
        return recruitPost;
    }
    
    @Transactional
    public Integer updateImg(RecruitPostUpdateDto dto, MultipartFile file) 
    		throws Exception {
    	log.info("update(dto={})", dto);
    	
    	RecruitPost entity = recruitPosrRepository.findById(dto.getId()).get();
    	
        String fileSavePath = "/img/"  + file.getOriginalFilename();
        String fileStaticPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
        
        File saveFile = new File(fileStaticPath, file.getOriginalFilename());
        file.transferTo(saveFile);
        
        dto.setFilePath(file.getOriginalFilename());
        dto.setFileName(fileSavePath);
    	
    	entity.updateRecruitPost(dto.getTitle(), dto.getContent(), dto.getPlace(), 
    			dto.getTotalMember(), dto.getFileName(), dto.getFilePath(), dto.getCloseDate());
    	
    	
    	return entity.getId();
    }

    @Transactional
    public Integer update(RecruitPostUpdateDto dto ){
        log.info("update(dto={})", dto);
        
        RecruitPost entity = recruitPosrRepository.findById(dto.getId()).get();
        
            entity.updateRecruitPost(dto.getTitle(), dto.getContent(), dto.getPlace(), 
                    dto.getTotalMember(), dto.getCloseDate(), dto.getLat(), dto.getLng());
        
        
        return entity.getId();
    }
    
    
    public List<RecruitPost> search(String type, String keyword) {
        log.info("search(type={}, keyword={}", type, keyword);
        
        List<RecruitPost> list = new ArrayList<>();
        switch(type) {
        case "t":
            list = recruitPosrRepository.searchByTitle(keyword);
            break;
        case "c":
            list = recruitPosrRepository.searchByKeyword(keyword);
            break;
        case "tc":
            list = recruitPosrRepository.searchByKeyword(keyword);
            break;
        case "a":
            list = recruitPosrRepository.searchByAuthor(keyword);
            break;
        case "category":
            list = recruitPosrRepository.searchByCategory(keyword);
        }
        return list;
    }
    
    public Page<RecruitPost> getMapList(int page) {
    	Pageable pageable = PageRequest.of(page, 15);
    	return this.recruitPosrRepository.findWhereCompareWithSysdate(pageable);
    }
    
    public Page<RecruitPost> getPostList(int page) {
        Pageable pageable = PageRequest.of(page, 15);
        return this.recruitPosrRepository.findByOrderByCreatedTimeDesc(pageable);
    }
    
    @Transactional
    public void deleteByAuthor(String author) {
        log.info("(delete={})", author);
        
        List<RecruitPost> list = recruitPosrRepository.findByAuthorOrderByIdDesc(author);
        
        for (RecruitPost a : list) {
            recruitPosrRepository.deleteById(a.getId());
        }
    }
    
 // 조회수 서비스
    @Transactional
    public Integer updateView(Integer id) {
        return recruitPosrRepository.updateViewCount(id);
    }
    
    @Transactional
    public RecruitPost bestLikecount(Integer i) {
        RecruitPost best;
        List<RecruitPost> list=recruitPosrRepository.findByOrderByLikecountDesc();
        
        if (list.size()==0) {
            return null;
        }
        
        if (i>list.size()-1) {
            return list.get(list.size()-1);
        }
        best=list.get(i);
        
        return best;
    }
    
    @Transactional
    public RecruitPost bestViewcount() {
        Double viewRise=0.0;
        RecruitPost best=null;
        List<RecruitPost> list=recruitPosrRepository.findByOrderByIdDesc();
        
        for (RecruitPost r : list) {
            Duration duration = Duration.between(r.getCreatedTime(), LocalDateTime.now());
            if (viewRise <= (double) r.getViewcount()/duration.getSeconds()) {
                viewRise=(double) r.getViewcount()/duration.getSeconds();
                best=r;
            }
        }
        
        return best;
    }
    
    
}
