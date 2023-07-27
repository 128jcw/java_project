package com.example.project5.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.project5.domain.FreeSharePost;
import com.example.project5.domain.RecruitPost;
import com.example.project5.dto.FreeSharePostCreateDto;
import com.example.project5.dto.FreeSharePostUpdateDto;
import com.example.project5.repository.FreeSharePostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FreeSharePostService {
    private final FreeSharePostRepository freeSharePostRepository;

    public List<FreeSharePost> read() {
        log.info("List<all> read");
        
        return freeSharePostRepository.findByOrderByIdDesc();
    }
    
    @Transactional(readOnly = true)
    public List<FreeSharePost> readByAuthor(String author) {
        log.info("readByAuthor={}", author);
        return freeSharePostRepository.findByAuthorOrderByIdDesc(author);
    }

    public FreeSharePost create(FreeSharePostCreateDto dto, MultipartFile file) throws Exception {
        log.info("create(dto={})",dto);
        FreeSharePost freeSharePost=dto.toEntity();
        String projectPath=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";
       
        UUID uuid=UUID.randomUUID();
        String fileName = uuid+"_"+file.getOriginalFilename();
        File saveFile=new File(projectPath, fileName);
        file.transferTo(saveFile);
        freeSharePost.setFileName(fileName);        //생성한 파일이름을 저장해줌.
        freeSharePost.setFilePath("/files/" + fileName);
        
        return freeSharePostRepository.save(freeSharePost);
    }
    
    public Page<FreeSharePost> getList(int page){
        log.info("page-getList(page={})",page);
        List<Sort.Order> sorts=new ArrayList<>();
        sorts.add(Sort.Order.desc("createdTime"));
        Pageable pageable=PageRequest.of(page, 9, Sort.by(sorts));
        
        return this.freeSharePostRepository.findAll(pageable);
    }
    
    public Page<FreeSharePost> getListFree(int page){
        log.info("page-getListFree(page={})",page);
        List<Sort.Order> sorts=new ArrayList<>();
        sorts.add(Sort.Order.desc("createdTime"));
        Pageable pageable=PageRequest.of(page, 8, Sort.by(sorts));
        
        return this.freeSharePostRepository.findByPriceFree(pageable);
    }
    
    public Page<FreeSharePost> getListToday(int page){
        log.info("page-getListToday(page={})",page);
        List<Sort.Order> sorts=new ArrayList<>();
        sorts.add(Sort.Order.desc("createdTime"));
        Pageable pageable=PageRequest.of(page, 8, Sort.by(sorts));
        
        return this.freeSharePostRepository.findByToday(pageable);
    }

    public FreeSharePost read(Integer id) {
        log.info("read(id={})", id);
        
        return freeSharePostRepository.findById(id).get();
    }

    public Integer delete(Integer id) {
        log.info("delete(id={})", id);
        freeSharePostRepository.deleteById(id);
        
        return id;
    }

    public Integer update(FreeSharePostUpdateDto dto) {
        log.info("update(dto={})", dto);
        FreeSharePost post=freeSharePostRepository.findById(dto.getId()).get();
        post.updateFreeSharePost(dto.getTitle(), dto.getContent(), dto.getPrice());
        freeSharePostRepository.save(post);
        
        return post.getId();
    }

    public List<FreeSharePost> search(String type, String keyword) {
        log.info("search(type={}, keyword={})",type,keyword);
        List<FreeSharePost> list = new ArrayList<>();
        switch(type) {
        case "t":
            list = freeSharePostRepository.searchByTitle(keyword);
            break;
        case "c":
            list = freeSharePostRepository.searchByContent(keyword);
            break;
        case "a":
            list = freeSharePostRepository.searchByAuthor(keyword);
            break;
        }
        
        return list;
    }
    
    @Transactional
    public void deleteByAuthor(String author) {
        log.info("delete(author={})", author);
        
        List<FreeSharePost> list = freeSharePostRepository.findByAuthorOrderByIdDesc(author);
        
        for (FreeSharePost a : list) {
            freeSharePostRepository.deleteById(a.getId());
        }
    }
    
}