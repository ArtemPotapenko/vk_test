package ru.vk_test.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import ru.vk_test.dto.Album;
import ru.vk_test.dto.Post;

import java.util.List;

@SpringBootTest
public class AlbumServiceTest {
    @Autowired
    AlbumService albumService;
    @Test
    public void getAll(){
       List<Album> albums = albumService.getAlbumsAll();
       Assertions.assertEquals(albumService.getAlbumById(1L), albums.get(0));
       Assertions.assertEquals(albumService.getAlbumById(5L), albums.get(4));
       Assertions.assertEquals(albumService.getAlbumById(100L), albums.get(99));
    }
    @Test
    public void getEmptyAlbum(){
        Assertions.assertThrows(HttpClientErrorException.class, ()-> albumService.getAlbumById(101L));
    }
}
