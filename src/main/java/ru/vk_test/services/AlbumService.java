package ru.vk_test.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vk_test.dto.Album;
import ru.vk_test.dto.Post;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
@Service
public class AlbumService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL = "https://jsonplaceholder.typicode.com/albums";
    @Cacheable("album")
    public Album getAlbumById(Long id){
        return restTemplate.getForObject(URL +"/"+id.toString(),Album.class);
    }
    @Cacheable("album")
    public List<Album> getAlbumsAll(){
        return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(URL, Album[].class))).toList();
    }
    @CachePut("album")
    public Album addAlbum(Album album){
        return restTemplate.postForObject(URL,album, Album.class);
    }
    @CachePut("album")
    public Album updateAlbum(Long id, Album album){
        return restTemplate.exchange(URL + "/" + id, HttpMethod.PUT,new HttpEntity<>(album), Album.class).getBody();
    }
    @CacheEvict("album")
    public void deleteAlbum(Long id){
        restTemplate.delete(URL + "/" + id);
    }
}
