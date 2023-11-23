package com.experis.course.springilmiofotoalbum.controller;

import com.experis.course.springilmiofotoalbum.dto.PhotoDto;
import com.experis.course.springilmiofotoalbum.model.Photo;
import com.experis.course.springilmiofotoalbum.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    PhotoService photoService;

    @GetMapping
    public String index(
            @RequestParam Optional<String> search,
            Model model
    ) {
        model.addAttribute(
                "photoList",
                photoService.getPhotoList(search)
        );
        return "photos/list";
    }

    @GetMapping("/show/{id}")
    public String show(
            @PathVariable Integer id,
            Model model
    ) {
        try {
            model.addAttribute("photo", photoService.getPhotoById(id));
            return "photos/show";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("photo", new PhotoDto());
        return "photos/form";
    }

    @PostMapping("/create")
    public String store(
            @Valid @ModelAttribute("photo") PhotoDto formPhoto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "photos/form";
        }

        try {
            Photo newPhoto = photoService.createPhoto(formPhoto);
            redirectAttributes.addFlashAttribute(
                    "message",
                    "Photo: " + newPhoto.getTitle() + " created"
            );
            return "redirect:/photos/show/" + newPhoto.getId();

        } catch (IOException e) {
            bindingResult.addError(new FieldError(
                    "photo",
                    "imageFile",
                    null,
                    false,
                    null,
                    null,
                    "Unable to save file"
            ));
            return "photos/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Integer id,
            Model model
    ) {
        try {
            model.addAttribute("photo", photoService.getPhotoDtoById(id));
            return "photos/form";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/edit/{id}")
    public String store(
            @PathVariable Integer id,
            @Valid @ModelAttribute PhotoDto formPhoto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "photos/form";
        }

        try {
            Photo editedPhoto = photoService.editPhoto(formPhoto);
            redirectAttributes.addFlashAttribute(
                    "message",
                    "Photo: " + editedPhoto.getTitle() + " edited"
            );
            return "redirect:/photos/show/" + editedPhoto.getId();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IOException e) {
            bindingResult.addError(new FieldError(
                    "photo",
                    "imageFile",
                    null,
                    false,
                    null,
                    null,
                    "Unable to save file"
            ));
            return "photos/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Photo deletedPhoto = photoService.getPhotoById(id);
            photoService.deletePhoto(id);
            redirectAttributes.addFlashAttribute(
                    "message",
                    "Photo: " + deletedPhoto.getTitle() + " delited"
            );
            return "redirect:/photos";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
