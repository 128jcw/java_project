package com.example.project5.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project5.domain.FestivalPost;

// JpaRepository<엔터티 클래스 타입, PK 데이터 타입>을 상속하는 인터페이스를 선언.

public interface FestivalPostRepository extends JpaRepository<FestivalPost, Integer> {
    
    
    Page<FestivalPost> findAll(Pageable pageable);
    // select * from COMMUNITYS order by ID desc
    List<FestivalPost> findByOrderByIdDesc();
    
    
    // 제목 검색:
    // select * from COMMUNITYS where lower(TITLE) like lower(?) order by ID desc
    List<FestivalPost> findByTitleIgnoreCaseContainingOrderByIdDesc(String title);
    
    // 내용 검색:
    // select * from COMMUNITYS where lower(CONTENT) like lower(?) order by ID desc
    List<FestivalPost> findByContentIgnoreCaseContainingOrderByIdDesc(String content);

    // 제목 + 내용 검색:
    // select * from COMMUNITYS 
    // where lower(TITLE) like lower(?1) or lower(CONTENT) like lower(?2)
    // order by ID desc
    List<FestivalPost> findByTitleIgnoreCaseContainingOrContentIgnoreCaseContainingOrderByIdDesc(String title, String content);
    
    // 축제지역명 검색:
    // select * from COMMUNITYS where lower(CONTENT) like lower(?) order by ID desc
    List<FestivalPost> findByFestivalAreaIgnoreCaseContainingOrderByIdDesc(String festivalArea);
    
    // JPQL(Java Persistence Query Language)
    @Query(
        "select p from COMMUNITYS p "
            + " where lower(p.title) like lower('%' || :keyword || '%') "
            + " or lower(p.content) like lower('%' || :keyword || '%') "
            + " order by p.id desc"
    )
    List<FestivalPost> searchByKeyword(@Param(value = "keyword") String keyword);

    
}
