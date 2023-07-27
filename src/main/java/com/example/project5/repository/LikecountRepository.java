package com.example.project5.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project5.domain.LikeCount;
import com.example.project5.domain.RecruitPost;

public interface LikecountRepository extends JpaRepository<LikeCount, Integer>{
    
    Optional<LikeCount> findByUsernameAndRecruitPostId(@Param(value = "username") String username , @Param(value="recruitPostId") Integer recruitPostId);
    
    @Modifying
    @Query("delete from LIKECOUNT l where l.username = :username and l.recruitPost.id = :recruitPostId")
    Integer deleteByRecruitPostId(@Param(value="username") String username, @Param(value="recruitPostId") Integer recruitPostId);

    List<LikeCount> findByRecruitPostIdOrderByIdDesc(Integer recruitPostId);

    List<LikeCount> findByUsernameOrderByIdDesc(String username);
    

}
 