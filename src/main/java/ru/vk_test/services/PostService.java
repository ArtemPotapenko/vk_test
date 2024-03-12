package ru.vk_test.services;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vk_test.dto.Post;
import java.util.*;
@Service
public class PostService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL = "https://jsonplaceholder.typicode.com/posts";
    @Cacheable("post")
    public Post getPostById(Long id){
        return restTemplate.getForObject(URL +"/"+id.toString(),Post.class);
    }
    @Cacheable("post")
    public List<Post> getPostsAll(){
        return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(URL, Post[].class))).toList();
    }
    @CachePut("post")
    public Post addPost(Post post){ 
        return restTemplate.postForObject(URL,post, Post.class);
    }
    @CachePut("post")
    public Post updatePost(Long id, Post post){
        return restTemplate.exchange(URL + "/" + id, HttpMethod.PUT,new HttpEntity<>(post), Post.class).getBody();
    }
    @Cacheable("post")
    public void deletePost(Long id){
        restTemplate.delete(URL + "/" + id);
    }

}
