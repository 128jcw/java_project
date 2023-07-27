package com.example.project5.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project5.domain.Member;
import com.example.project5.domain.MemberRole;
import com.example.project5.dto.MemberRegisterDto;
import com.example.project5.dto.MemberUpdateDto;
import com.example.project5.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    
    public String checkUsername(String username) {
        log.info("checkUsername(username={})", username);
        
        Optional<Member> result = memberRepository.findByUsername(username);
        
        if (result.isPresent()) { //아이디 중복체크
            return "nok";
        } else {
            return "ok";
        }
        
    }
    
    public Member RegisterMember(MemberRegisterDto dto) {
        log.info("RegisterMember(dto={})", dto);
        
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Member entity=memberRepository.save(dto.toEntity());
        log.info("entity = {}", entity);
        
        return entity;
    }
    
    public Member findByUsername(String username) {
        log.info("findByUsername(username={})", username);
        return memberRepository.findByUsername(username).get();
    }
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("findByUsername(username={})", username);
        
        Optional<Member> member = memberRepository.findByUsername(username);
        
        Member memberEntity = member.get();
        
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin").equals(username)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getRole()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getRole()));
        }
        
        return new User(memberEntity.getUsername(), memberEntity.getPassword(), authorities);
    }
    
    @Transactional
    public Integer update(MemberUpdateDto dto) {
        log.info("update(dto={})", dto);
        
        Member entity = memberRepository.findById(dto.getId()).get();
        
        entity.update(dto.getNickname(), dto.getName(), dto.getPhone(), dto.getEmail());
        
        return entity.getId();
    }
    
    @Transactional
    public void delete(String username) {
        log.info("delete(username={})", username);
        
        memberRepository.deleteByUsername(username);
    }
    
    @Transactional
    public void updatePassword(String password, String username) {
        log.info("updatePassword(password={}, username={})", password, username);
        
        Member member=memberRepository.findByUsername(username).get();
        member.setPassword(passwordEncoder.encode(password));
        
        memberRepository.save(member);
    }

}
