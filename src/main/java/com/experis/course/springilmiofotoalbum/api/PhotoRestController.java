package com.experis.course.springilmiofotoalbum.api;

import com.experis.course.springilmiofotoalbum.dto.PhotoDto;
import com.experis.course.springilmiofotoalbum.model.Photo;
import com.experis.course.springilmiofotoalbum.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/photos")
@CrossOrigin
public class PhotoRestController {
    @Autowired
    PhotoService photoService;

    @GetMapping
    public List<Photo> index(@RequestParam Optional<String> search) {
        return photoService.getVisiblePhotoList(search);
    }

    @GetMapping("/{id}")
    public Photo read(@PathVariable Integer id) {
        try {
            return photoService.getPhotoById(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/create")
    public Photo create(@Valid @RequestBody PhotoDto photoDto) {
        try {
            return photoService.createPhoto(photoDto);
        } catch (RuntimeException | IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/edit/{id}")
    public Photo update(
            @PathVariable Integer id,
            @Valid @RequestBody PhotoDto photoDto
    ) {
        try {
            return photoService.editPhoto(photoDto);
        } catch (RuntimeException | IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        try {
            photoService.deletePhoto(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
