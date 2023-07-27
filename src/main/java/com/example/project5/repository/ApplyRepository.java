package com.example.project5.repository;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project5.domain.Apply;

public interface ApplyRepository extends JpaRepository<Apply, Integer> {
    
    Optional<Apply> findByJoinNicknameAndRecruitPostId(@Param(value = "joinNickname") String joinNickname , @Param(value = "recruitPostId") Integer recruitPostId); 
    
    List<Apply> findByJoinNicknameOrderByIdDesc(String joinNickname);
    
    List<Apply> findByRecruitPostIdOrderByIdDesc(Integer recruitPostId);

    @Transactional
    @Modifying
    @Query("delete from APPLY a where a.joinNickname = :joinNickname and a.recruitPost.id = :recruitPostId")
    int deleteByjoinNickname(@Param(value = "joinNickname") String joinNickname,
            @Param(value = "recruitPostId") Integer recruitPostId);
    
    
//    Optional<Apply> findByjoinNicknameAndRecruitPostId(@Param(value = "joinNickname") String joinNickname, @Param(value = "recruitPostId") Integer recruitPostId);
}
    