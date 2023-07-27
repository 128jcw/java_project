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

import com.example.project5.domain.FestivalPost;
import com.example.project5.dto.FestivalPostCreateDto;
import com.example.project5.dto.FestivalPostUpdateDto;
import com.example.project5.repository.FestivalPostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // final 필드를 초기화하는 생성자.
@Service // 스프링 컨텍스트에 Service 컴포넌트로 등록.
public class FestivalPostService {
    private final FestivalPostRepository festivalPostRepository;
    
    @Transactional(readOnly = true) // 검색 속도가 빨라짐.
    public List<FestivalPost> read() {
        log.info("read()");
        
        return festivalPostRepository.findByOrderByIdDesc();
    }
    
    
    public FestivalPost create(FestivalPostCreateDto dto, MultipartFile file) throws IOException {
        log.info("create(dto={})", dto);
        
//        FestivalPost entity = festivalPostRepository.save(dto.toEntity()); 
        FestivalPost entity = dto.toEntity();
        
        // 파일 저장 경로 설정
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\festivalImg";
        
        UUID uuid = UUID.randomUUID();
        // 파일 고유 이름 랜덤 생성
        String fileName = uuid + "_" + file.getOriginalFilename();
        
        File saveFile = new File(filePath ,fileName); // 파일 저장소
        file.transferTo(saveFile); // throws Exception
        
        entity.setFileName(fileName);
        entity.setFilePath("/festivalImg/" + fileName);
        
        return festivalPostRepository.save(entity);
        
    }
    
    
    public Page<FestivalPost> getList(int page){ 
      log.info("page-getList(page={})",page); // List<Sort.Order> sorts=new
      
      List<Sort.Order> sorts=new ArrayList<>();
      sorts.add(Sort.Order.desc("createdTime"));
      Pageable pageable=PageRequest.of(page, 4, Sort.by(sorts));
      
      return this.festivalPostRepository.findAll(pageable);
     }
       
    @Transactional(readOnly = true)
    public FestivalPost read(Integer id) {
        log.info("read(id={})", id);
        
        return festivalPostRepository.findById(id).get();
    }
    
    public Integer delete(Integer id) {
        log.info("delete(id={})", id);
        
        festivalPostRepository.deleteById(id);
        
        return id; // 삭제한 포스트 아이디(번호)를 리턴.
    }
    
    @Transactional
    public Integer updateImg(FestivalPostUpdateDto dto, MultipartFile file) 
            throws Exception {
        log.info("update(dto={})", dto);
        
        FestivalPost entity = festivalPostRepository.findById(dto.getId()).get();
        
        String fileSavePath = "/img/"  + file.getOriginalFilename();
        String fileStaticPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
        
        File saveFile = new File(fileStaticPath, file.getOriginalFilename());
        file.transferTo(saveFile);
        
        dto.setFilePath(file.getOriginalFilename());
        dto.setFileName(fileSavePath);
        
        entity.updateFestivalPost(dto.getTitle(), dto.getContent(), 
                dto.getFestivalAgency(), dto.getFestivalArea(), dto.getFestivalCharacter(),
                dto.getFestivalInfo(), dto.getFestivalInquiry(), dto.getFestivalPeriod(),
                dto.getFestivalPrice(), dto.getFestivalPlace(), 
                dto.getFileName(), dto.getFilePath());
        
        
        return entity.getId();
    }
    
    @Transactional // readOnly = false(기본값)
    public Integer update(FestivalPostUpdateDto dto) {
        log.info("update(dto={})", dto);
        
        FestivalPost entity = festivalPostRepository.findById(dto.getId()).get(); // (1)
        
        entity.updateFestivalPost(dto.getTitle(), dto.getContent(), 
                dto.getFestivalAgency(), dto.getFestivalArea(), dto.getFestivalCharacter(),
                dto.getFestivalInfo(), dto.getFestivalInquiry(), dto.getFestivalPeriod(),
                dto.getFestivalPrice(), dto.getFestivalPlace()); // (2)
        
        return entity.getId();
    }

    public List<FestivalPost> search(String type, String keyword) {
        log.info("search(type={}, keyword={})", type, keyword);
        
        List<FestivalPost> list = new ArrayList<>();
        switch (type) {
        case "t": // 제목만 검색
            list = festivalPostRepository.findByTitleIgnoreCaseContainingOrderByIdDesc(keyword);
            break;
        case "c": // 내용만 검색
            list = festivalPostRepository.findByContentIgnoreCaseContainingOrderByIdDesc(keyword);
            break;
        case "tc": // 제목 또는 내용 검색
            list = festivalPostRepository.searchByKeyword(keyword);
            break;
        case "a" : // 축제지역명 검색
            list = festivalPostRepository.findByFestivalAreaIgnoreCaseContainingOrderByIdDesc(keyword);
        }
        
        return list;
    }
    
    
}
