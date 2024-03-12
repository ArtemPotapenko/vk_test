package ru.vk_test.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonSerialize
public class Album {
    private Long id;
    private Long userId;
    private String title;
}
