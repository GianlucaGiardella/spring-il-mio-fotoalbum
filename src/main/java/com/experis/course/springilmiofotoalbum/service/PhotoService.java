package com.experis.course.springilmiofotoalbum.service;

import com.experis.course.springilmiofotoalbum.dto.PhotoDto;
import com.experis.course.springilmiofotoalbum.model.Photo;
import com.experis.course.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;

    public List<Photo> getPhotoList(Optional<String> search) {
        if (search.isPresent()) {
            return photoRepository.findByTitleContainingIgnoreCase(search.get());
        } else {
            return photoRepository.findAll();
        }
    }

    public Photo getPhotoById(Integer id) throws RuntimeException {
        Optional<Photo> result = photoRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("Photo with id " + id + " not found");
        }
    }

    public PhotoDto getPhotoDtoById(Integer id) throws RuntimeException {
        Photo photo = getPhotoById(id);
        return convertPhotoToDto(photo);
    }

    private static Photo convertDtoToPhoto(PhotoDto photoDto) throws IOException {
        Photo photo = new Photo();
        photo.setId(photo.getId());
        photo.setTitle(photoDto.getTitle());
        photo.setDescription(photoDto.getDescription());
        photo.setVisible(photoDto.isVisible());
        photo.setCategories(photoDto.getCategories());

        if (photoDto.getImageFile() != null && !photoDto.getImageFile().isEmpty()) {
            byte[] bytes = photoDto.getImageFile().getBytes();
            photo.setImage(bytes);
        }
        return photo;
    }

    private static PhotoDto convertPhotoToDto(Photo photo) {
        PhotoDto photoDto = new PhotoDto();
        photoDto.setId(photo.getId());
        photoDto.setTitle(photo.getTitle());
        photoDto.setDescription(photo.getDescription());
        photoDto.setVisible(photo.isVisible());
        photoDto.setCategories(photo.getCategories());
        return photoDto;
    }

    public Photo createPhoto(PhotoDto photoDto) throws IOException {
        Photo photo = convertDtoToPhoto(photoDto);

        photo.setId(null);

        return photoRepository.save(photo);
    }

    public Photo editPhoto(PhotoDto photoDto) throws IOException, RuntimeException {
        Photo photo = convertDtoToPhoto(photoDto);

        Photo dbPhoto = getPhotoById(photoDto.getId());
        dbPhoto.setTitle(photo.getTitle());
        dbPhoto.setDescription(photo.getDescription());
        dbPhoto.setVisible(photo.isVisible());
        dbPhoto.setCategories(photo.getCategories());

        if (photo.getImage() != null && photo.getImage().length > 0) {
            dbPhoto.setImage(photo.getImage());
        }

        return photoRepository.save(dbPhoto);
    }

    public void deletePhoto(Integer id) {
        photoRepository.deleteById(id);
    }
}
