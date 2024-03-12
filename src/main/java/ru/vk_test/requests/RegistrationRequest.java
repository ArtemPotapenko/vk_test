package ru.vk_test.requests;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@JsonSerialize
@Data
public class RegistrationRequest {
    private String username;
    private String[] roles;
    private String password;
}
