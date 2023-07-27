package com.example.project5.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.project5.domain.Member;
import com.example.project5.dto.MemberSecurityDto;
import com.example.project5.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final MemberRepository memberRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> entity = memberRepository.findByUsername(username);
        if (entity.isPresent()) {
            return MemberSecurityDto.fromEntity(entity.get());
        } else {
            throw new UsernameNotFoundException(username + ": not found");
        }
        
    }

}
