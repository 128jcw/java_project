package com.example.project5.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project5.domain.RecruitPost;
import com.example.project5.domain.ReviewPost;
import com.example.project5.dto.ReviewPostCreateDto;
import com.example.project5.dto.ReviewPostUpdateDto;
import com.example.project5.repository.ReviewPostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewPostService {
    
    private final ReviewPostRepository reviewPostRepository;
    
    @Transactional(readOnly = true)
    public List<ReviewPost> read() {
        log.info("read()");
        return reviewPostRepository.findByOrderByIdDesc();
    }
    
    @Transactional(readOnly = true)
    public List<ReviewPost> readByAuthor(String author) {
        log.info("readByAuthor(author={})", author);
        return reviewPostRepository.findByAuthorOrderByIdDesc(author);
    }
    
    @Transactional(readOnly = true)
    public ReviewPost read(Integer id) {
        log.info("read(id={})", id);
        return reviewPostRepository.findById(id).get();
    }
    
    public Integer delete(Integer id) {
        log.info("delete(id={})", id);
        reviewPostRepository.deleteById(id);
        return id;
    }
    
    public ReviewPost create(ReviewPostCreateDto dto) {
        log.info("create(dto={})");
        
        ReviewPost reviewPost=dto.toEntity();
        reviewPost=reviewPostRepository.save(reviewPost);
        
        return reviewPost;
    }
    
    @Transactional
    public Integer update(ReviewPostUpdateDto dto) {
        log.info("update(dto={})", dto);
        
        ReviewPost entity=reviewPostRepository.findById(dto.getId()).get();
        
        entity.updateReviewPost(dto.getTitle(), dto.getContent(), dto.getAuthor(), 
                dto.getOrganizer(), dto.getActivity());
        
        return entity.getId();
    }
    
    @Transactional
    public void deleteByAuthor(String author) {
        log.info("(delete={})", author);
        
        List<ReviewPost> list=reviewPostRepository.findByAuthorOrderByIdDesc(author);
        
        for (ReviewPost a : list) {
            reviewPostRepository.deleteById(a.getId());
        }
        
    }
    
    public Page<ReviewPost> getPostList(int page) {
    	Pageable pageable = PageRequest.of(page, 15);
    	return this.reviewPostRepository.findByOrderByCreatedTimeDesc(pageable)
;    }
    
    public List<ReviewPost> search(String type, String keyword) {
        log.info("search(type={}, keyword={}", type, keyword);
        
        List<ReviewPost> list = new ArrayList<>();
        switch(type) {
        case "t":
            list = reviewPostRepository.searchByTitle(keyword);
            break;
        case "o":
            list = reviewPostRepository.searchByOrganizer(keyword);
            break;
        case "a":
            list = reviewPostRepository.searchByAuthor(keyword);
            break;
   
        }
        return list;
    }

}
