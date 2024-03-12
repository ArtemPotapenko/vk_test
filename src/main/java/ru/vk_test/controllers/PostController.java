package ru.vk_test.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.vk_test.dto.Post;
import ru.vk_test.services.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable Long id) {
        log.info("Get post id " + id);
        return postService.getPostById(id);
    }

    @GetMapping("/api/posts")
    public List<Post> getAllPost() {
        log.info("Get all posts");
        return postService.getPostsAll();
    }

    @PostMapping("/api/posts")
    public Post addPost(@RequestParam Post post) {
        log.info("Add post");
        return postService.addPost(post);
    }

    @PutMapping("/api/posts/{id}")
    public Post updatePost(@RequestParam Post post, @PathVariable Long id) {
        log.info("Update post " + id);
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/api/posts/{id}")
    public void removePost(@PathVariable Long id){
        log.info("Remove post  " + id);
        postService.deletePost(id);
    }

}
