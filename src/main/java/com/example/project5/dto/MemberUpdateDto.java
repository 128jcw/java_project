package com.example.project5.dto;

import com.example.project5.domain.Member;

import lombok.Data;

@Data
public class MemberUpdateDto {
    private Integer id;
    private String nickname;
    private String name;
    private String phone;
    private String email;
    
    public Member toEntity() {
        return Member.builder()
                .id(id).nickname(nickname)
                .name(name).phone(phone)
                .email(email).build();
    }
}
