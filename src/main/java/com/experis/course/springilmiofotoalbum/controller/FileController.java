package com.experis.course.springilmiofotoalbum.controller;

import com.experis.course.springilmiofotoalbum.model.Photo;
import com.experis.course.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
public class FileController {
    @Autowired
    PhotoService photoService;

    @GetMapping("/image/{photoId}")
    public ResponseEntity<byte[]> serveImage(
            @PathVariable Integer photoId
    ) {
        try {
            Photo photo = photoService.getPhotoById(photoId);
            byte[] bytes = photo.getImage();

            if (bytes != null && bytes.length > 0) {
                MediaType mediaType = MediaType.IMAGE_JPEG;
                return ResponseEntity.ok().contentType(mediaType).body(bytes);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
