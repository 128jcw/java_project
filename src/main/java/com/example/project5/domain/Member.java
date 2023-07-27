package com.example.project5.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name = "MEMBERS")
@SequenceGenerator(name = "MEMBERS_SEQ_GEN", sequenceName = "MEMBERS_SEQ1", allocationSize = 1)
public class Member extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBERS_SEQ_GEN")
    private Integer id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String nickname;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String gender;
    
    private Integer postCount;
    
    @Column(nullable = false)
    private String likeField;
    
    private boolean deleted;
    
    private boolean social;
    

	@ElementCollection(fetch = FetchType.LAZY)
	@Builder.Default
	private Set<MemberRole> roles=new HashSet<>();
	    
	public Member addRole(MemberRole role) {
	    roles.add(role);
	        
	    return this;
	}
    
    
    public Member update(String nickname, String name, String phone, String email) {
        this.nickname=nickname;
        this.name=name;
        this.phone=phone;
        this.email=email;
        
        return this;
    }

}
