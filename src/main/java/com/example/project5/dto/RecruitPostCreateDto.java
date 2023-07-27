package com.example.project5.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.example.project5.domain.RecruitPost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitPostCreateDto {
	
	private Integer id;
    private String title;
    private String content;
    private String author;
    private String place;
    
    private String category;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime meetingDate;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime closeDate;

    private Integer totalMember;
    
    private String fileName;
    private String filePath;
    
    
    private double lat;
    private double lng;

    public RecruitPost toEntity() {
        return RecruitPost.builder().title(title).content(content).author(author).place(place).meetingDate(meetingDate)
                .lat(lat).lng(lng).fileName(fileName).filePath(filePath).totalMember(totalMember).closeDate(closeDate).category(category)
                .build();
    }

}
