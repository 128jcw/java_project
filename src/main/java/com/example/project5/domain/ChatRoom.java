package com.example.project5.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="CHATROOM")
@SequenceGenerator(name="CHATROOM_SEQ_GEN" , sequenceName = "CHATROOM_SEQ", allocationSize = 1)
public class ChatRoom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHATROOM_SEQ_GEN")
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String roomId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    
    private String message;

    private Integer unread;
    
    private String leaveUser;

    public ChatRoom(String roomId, Member member, String leaveUser, Integer unread) {
        this.roomId = roomId;
        this.member = member;
        this.leaveUser = leaveUser;
        this.unread = unread;
    }

}