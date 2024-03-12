package ru.vk_test.requests;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@JsonSerialize
@Data
public class LoginRequest {
    private String login;
    private String password;
}
