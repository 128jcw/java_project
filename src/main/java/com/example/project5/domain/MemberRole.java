package com.example.project5.domain;

// 사용자(회원)의 역할(권한) - 일반사용자, 관리자
// -> Spring Security에사 사용됨.
public enum MemberRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");
    
    // 필드(멤버 변수)
    private String role;
    
    // 생성자
    MemberRole(String role) {
        this.role=role;
    }
    
    // getter
    public String getRole() {
        return this.role;
    }
    
}
