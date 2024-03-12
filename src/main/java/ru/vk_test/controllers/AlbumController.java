package ru.vk_test.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.vk_test.dto.Album;
import ru.vk_test.services.AlbumService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping("/api/albums/{id}")
    public Album getAlbum(@PathVariable Long id) {
        log.info("Get album id " + id);
        return albumService.getAlbumById(id);
    }

    @GetMapping("/api/albums")
    public List<Album> getAllAlbums() {
        log.info("Get all albums");
        return albumService.getAlbumsAll();
    }

    @PostMapping("/api/albums")
    public Album addAlbum(@RequestParam Album album) {
        log.info("Add album" + album);
        return albumService.addAlbum(album);
    }

    @PutMapping("/api/albums/{id}")
    public Album updateAlbum(@RequestParam Album album, @PathVariable Long id) {
        log.info("Update album " + id);
        return albumService.updateAlbum(id, album);
    }

    @DeleteMapping("/api/albums/{id}")
    public void removeAlbum(@PathVariable Long id){
        log.info("Remove album");
        albumService.deleteAlbum(id);
    }
}
