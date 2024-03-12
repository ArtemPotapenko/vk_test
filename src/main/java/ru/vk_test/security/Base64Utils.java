package ru.vk_test.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
@Slf4j
public class Base64Utils {
    public static String encode(String login, String password){
        return new String(Base64.getEncoder().encode((login + ":" + password).getBytes()));
    }
    public static boolean tokenIsValid(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return false;
        if (authHeader.length() < 7) return false;
        return authHeader.startsWith("Basic");
    }

    public static Map<String, String> decodeFromRequest(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        String token = authHeader.substring(6);
        log.info("Token {}", token);

        String[] usernameAndPassword  = new String(Base64.getDecoder().decode(token)).split(":");
        return Map.of("username", usernameAndPassword[0], "password", usernameAndPassword[1]);
    }
}
