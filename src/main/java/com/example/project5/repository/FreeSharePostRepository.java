package com.example.project5.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project5.domain.FreeSharePost;
import com.example.project5.domain.RecruitPost;

public interface FreeSharePostRepository extends JpaRepository<FreeSharePost, Integer> {

    List<FreeSharePost> findByOrderByIdDesc();
    Page<FreeSharePost> findAll(Pageable pageable);
    List<FreeSharePost> findByAuthorOrderByIdDesc(String author);
    
    // 무료인 게시글만 검색
    @Query(
        "select r from FREESHAREPOSTS r where r.price='0' order by r.id desc"
            )
    Page<FreeSharePost> findByPriceFree(Pageable pageable);
    
    // 오늘 올라온 게시글만 검색
    @Query(
        "select r from FREESHAREPOSTS r where TO_CHAR(sysdate, 'yyyy-mm-dd') = TO_CHAR(r.createdTime,'yyyy-mm-dd') order by r.id desc"
            )
    Page<FreeSharePost> findByToday(Pageable pageable);
    
    // 제목검색
    @Query(
        "select r from FREESHAREPOSTS r where lower(r.title) like lower('%' || :title || '%') order by r.id desc"
            )
    List<FreeSharePost> searchByTitle(@Param(value="title") String title);
        
    // 내용검색
    @Query(
        "select r from FREESHAREPOSTS r where lower(r.content) like lower('%' || :content || '%') order by r.id desc"
            )
    List<FreeSharePost> searchByContent(@Param(value="content") String content);

    // 작성자 검색
    @Query(
        "select r from FREESHAREPOSTS r where lower(r.author) like lower('%' || :author || '%') order by r.id desc"
            )
    List<FreeSharePost> searchByAuthor(@Param(value="author") String author);

    
    
}
