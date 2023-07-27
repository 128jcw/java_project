package com.example.project5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project5.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    
    @EntityGraph(attributePaths = "roles")
    Optional<Member> findByUsername(String username);
    
    int deleteByUsername(String username);
    
    @Modifying
    @Query(
            "update MEMBERS r set r.password=password where r.username=username"
            )
    int updatePassword(@Param(value="password") String password, @Param(value="username") String username);

}
