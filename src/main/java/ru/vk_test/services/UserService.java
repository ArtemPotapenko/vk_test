package ru.vk_test.services;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vk_test.dto.Post;
import ru.vk_test.dto.User;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL = "https://jsonplaceholder.typicode.com/users";

    @Cacheable("user")
    public User getUserById(Long id) {
        return restTemplate.getForObject(URL + "/" + id.toString(), User.class);
    }

    @Cacheable("user")
    public List<User> getUsersAll() {
        return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(URL, User[].class))).toList();
    }

    @CachePut("user")
    public User addUser(User user) {
        return restTemplate.postForObject(URL, user, User.class);
    }
    @CachePut("user")
    public User updateUser(Long id, User user) {
        return restTemplate.exchange(URL + "/" + id, HttpMethod.PUT, new HttpEntity<>(user), User.class).getBody();
    }

    @Cacheable("user")
    public void deleteUser(Long id) {
        restTemplate.delete(URL + "/" + id);
    }
}
