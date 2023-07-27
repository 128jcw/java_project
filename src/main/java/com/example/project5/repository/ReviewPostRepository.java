package com.example.project5.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project5.domain.RecruitPost;
import com.example.project5.domain.ReviewPost;

public interface ReviewPostRepository extends JpaRepository<ReviewPost, Integer> {
    
    List<ReviewPost> findByOrderByIdDesc();
    
    List<ReviewPost> findByAuthorOrderByIdDesc(String author);
    
    Page<ReviewPost> findByOrderByCreatedTimeDesc(Pageable pageable);
    
 // 제목검색 
 	@Query(
 		"select r from REVIEWPOSTS r where lower(r.title) like lower('%' || :title || '%') order by r.id desc"
 			)
 	List<ReviewPost> searchByTitle(@Param(value="title") String title);
 		
 	// 모임장 검색
 	@Query(
 		"select r from REVIEWPOSTS r where lower(r.organizer) like lower('%' || :organizer || '%') order by r.id desc"
 			)
 	List<ReviewPost> searchByOrganizer(@Param(value="organizer") String organizer);

 	// 작성자 검색
 	@Query(
 		"select r from REVIEWPOSTS r where lower(r.author) like lower('%' || :author || '%') order by r.id desc"
 			)
 	List<ReviewPost> searchByAuthor(@Param(value="author") String author);
     

}
