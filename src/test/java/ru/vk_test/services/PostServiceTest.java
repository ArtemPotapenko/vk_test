package ru.vk_test.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import ru.vk_test.dto.Post;

import java.util.List;

@SpringBootTest
public class PostServiceTest {
    @Autowired
    PostService postService;
    @Test
    public void getAll(){
        List<Post> postList = postService.getPostsAll();
        Assertions.assertEquals(postList.get(0),postService.getPostById(1L));
        Assertions.assertEquals(postList.get(1),postService.getPostById(2L));
        Assertions.assertEquals(postList.get(2),postService.getPostById(3L));
        Assertions.assertEquals(postList.get(99),postService.getPostById(100L));
    }
    @Test
    public void getFirst(){
        Assertions.assertEquals(postService.getPostById(1L), Post.builder( ).id(1L)
                .userId(1L).
                title("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")
                .body("""
                        quia et suscipit
                        suscipit recusandae consequuntur expedita et cum
                        reprehenderit molestiae ut ut quas totam
                        nostrum rerum est autem sunt rem eveniet architecto""").build());
    }
    @Test
    public void addPost(){
        Assertions.assertEquals(postService.addPost(Post.builder().title("111").userId(1L).body("111").build()).getId(),101L);
    }
    @Test
    public void getEmptyPost(){
        Assertions.assertThrows(HttpClientErrorException.class, ()-> postService.getPostById(101L));
    }
    @Test
    public void reducePost(){
        Assertions.assertEquals(postService.updatePost(1L,
                Post.builder().body("111").build()), Post.builder( ).id(1L).body("111").build());
    }

}
