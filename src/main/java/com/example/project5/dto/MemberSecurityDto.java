package com.example.project5.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.project5.domain.Member;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberSecurityDto extends User {
    
    private String username;
    private String password;
    private String nickname;
    private String name;
    private String phone;
    private String email;
    private String gender;
    private Integer postCount;
    private String likeField;
    
    public MemberSecurityDto(String username, String password, String nickname, String name, 
            String phone, String email, String gender, Integer postCount, String likeField, 
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.username=username;
        this.password=password;
        this.nickname=nickname;
        this.name=name;
        this.phone=phone;
        this.email=email;
        this.gender=gender;
        this.postCount=postCount;
        this.likeField=likeField;
    }
    
    public static MemberSecurityDto fromEntity(Member m) {
        List<GrantedAuthority> authorities = m.getRoles().stream()
                .map(x -> new SimpleGrantedAuthority(x.getRole()))
                .collect(Collectors.toList());
        
        MemberSecurityDto dto = new MemberSecurityDto(m.getUsername(), m.getPassword(), m.getNickname(), 
                m.getName(), m.getPhone(), m.getEmail(), m.getGender(), m.getPostCount(), 
                m.getLikeField(), authorities);
        
        return dto;
    }

}
