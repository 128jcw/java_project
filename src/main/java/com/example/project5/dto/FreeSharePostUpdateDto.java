package com.example.project5.dto;

import com.example.project5.domain.FreeSharePost;

import groovy.transform.ToString;
import lombok.Data;

@Data
@ToString
public class FreeSharePostUpdateDto {
    private Integer id;
    private String title;
    private String content;
    private String price;
    
    public FreeSharePost toEntity() {
        return FreeSharePost.builder().id(id).title(title).content(content).price(price).build();
    }
}
