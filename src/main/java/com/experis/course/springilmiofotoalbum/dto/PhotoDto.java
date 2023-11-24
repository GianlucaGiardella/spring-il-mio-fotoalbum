package com.experis.course.springilmiofotoalbum.dto;

import com.experis.course.springilmiofotoalbum.model.Category;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PhotoDto {
    private Integer id;
    @NotBlank(message = "Title must be non blank")
    @Size(min = 3, max = 50)
    private String title;
    @NotBlank(message = "Description must be non blank")
    @Size(min = 3)
    @Lob
    private String description;
    private MultipartFile imageFile;
    private boolean visible = true;
    private List<Category> categories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
