package ru.vk_test.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

@JsonSerialize
@Data
@Builder
public class Company {
    private String name;
    private String catchPhrase;
    private String bs;
}
