package ru.vk_test.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

@JsonSerialize
@Builder
@Data
public class Post {
    private Long id;
    private Long userId;
    private String title;
    private String body;

}
