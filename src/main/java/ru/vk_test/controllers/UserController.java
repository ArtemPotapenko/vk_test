package ru.vk_test.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.vk_test.dto.Post;
import ru.vk_test.dto.User;
import ru.vk_test.services.PostService;
import ru.vk_test.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/api/users/{id}")
    public User getUser(@PathVariable Long id) {
        log.info("Get user id " + id);
        return userService.getUserById(id);
    }

    @GetMapping("/api/users")
    public List<User> getAllUser() {
        log.info("Get all users");
        return userService.getUsersAll();
    }

    @PostMapping("/api/users")
    public User addUser(@RequestParam User user) {
        log.info("Add user");
        return userService.addUser(user);
    }

    @PutMapping("/api/users/{id}")
    public User updateUser(@RequestParam User user, @PathVariable Long id) {
        log.info("Update user " + id);
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/api/users/{id}")
    public void removePost(@PathVariable Long id){
        log.info("Remove user  " + id);
        userService.deleteUser(id);
    }
}
