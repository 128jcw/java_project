package com.example.project5.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project5.domain.RecruitPost;
import com.example.project5.domain.RecruitPostReply;

public interface RecruitPostRepository extends JpaRepository<RecruitPost, Integer> {

	List<RecruitPost> findByOrderByIdDesc();
	
	
	List<RecruitPost> findByAuthorOrderByIdDesc(String author);
	
	
	List<RecruitPost> findByOrderByViewcount();
	
	List<RecruitPost> findByOrderByLikecountDesc();

	// 제목검색
	@Query(
		"select r from RECRUITPOSTS r where lower(r.title) like lower('%' || :title || '%') order by r.id desc"
			)
	List<RecruitPost> searchByTitle(@Param(value="title") String title);
		
	// 내용검색
	@Query(
		"select r from RECRUITPOSTS r where lower(r.content) like lower('%' || :content || '%') order by r.id desc"
			)
	List<RecruitPost> searchByContent(@Param(value="content") String content);

	// 작성자 검색
	@Query(
		"select r from RECRUITPOSTS r where lower(r.author) like lower('%' || :author || '%') order by r.id desc"
			)
	List<RecruitPost> searchByAuthor(@Param(value="author") String author);

	// 제목, 내용검색
    @Query(
        "select r from RECRUITPOSTS r where lower(r.title) like lower('%' || :keyword || '%') "
            + " or lower(r.content) like lower('%' || :keyword || '%') order by r.id desc"
            )
    List<RecruitPost> searchByKeyword(@Param(value = "keyword") String keyword);
    
    // 카테고리검색
    @Query(
        "select r from RECRUITPOSTS r where lower(r.category) like lower('%' || :category || '%') order by r.id desc"
            )
    List<RecruitPost> searchByCategory(@Param(value="category") String category);
    
    
    Page<RecruitPost> findAll(Pageable pageable);
    
    
    Page<RecruitPost> findByOrderByIdDesc(Pageable pageable);
    
    Page<RecruitPost> findByOrderByCreatedTimeDesc(Pageable pageable);
    
    @Query(
		value="select * from recruitposts where TO_CHAR(SYSDATE, 'YYYY/MM/DD HH24:MI:SS') <= TO_CHAR(CLOSE_DATE, 'YYYY/MM/DD HH24:MI:SS')"
				+ "order by CREATED_TIME desc",
		nativeQuery = true)
    Page<RecruitPost> findWhereCompareWithSysdate(Pageable pageable);
    
    @Modifying
    @Query("update RECRUITPOSTS r set r.viewcount = r.viewcount + 1 where r.id = :id")
    Integer updateViewCount(@Param(value = "id") Integer Id);
    
    @Modifying
    @Query("update RECRUITPOSTS r set r.likecount = r.likecount + 1 where r.id = :id")
    Integer plusLikeCount(@Param(value = "id") Integer Id);
    
    @Modifying
    @Query("update RECRUITPOSTS r set r.likecount = r.likecount - 1 where r.id = :id")
    Integer minusLikeCount(@Param(value = "id") Integer Id);

}