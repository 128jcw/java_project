package com.example.project5.dto;

import com.example.project5.domain.Member;
import com.example.project5.domain.MemberRole;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberRegisterDto {
    private String username;
    private String password;
    private String nickname;
    private String name;
    private String phone;
    private String email;
    private String gender;
    private Integer postCount=0;
    private String likeField;
    
    public Member toEntity() {
        return Member.builder()
                .username(username).password(password).nickname(nickname)
                .name(name).phone(phone).email(email).gender(gender)
                .postCount(postCount).likeField(likeField).build()
                .addRole(MemberRole.USER);
    }
}
