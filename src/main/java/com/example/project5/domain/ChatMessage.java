package com.example.project5.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Entity(name = "CHATMESSAGE")
@SequenceGenerator(name = "CHATMESSAGE_SEQ_GEN",sequenceName = "CHATMESSAGE_SEQ", allocationSize = 1)
public class ChatMessage {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHATMESSAGE_SEQ_GEN")
    private Long id;
    private String roomId;
    
    private String sender;
    private String sendTime;

    private String message;
    private Integer messageNumber;
    
    public ChatMessage update(String roomId) {
        this.roomId = roomId;
        return this;
    }
}
