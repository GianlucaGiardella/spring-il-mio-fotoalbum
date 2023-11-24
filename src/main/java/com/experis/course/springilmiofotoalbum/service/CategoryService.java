package com.experis.course.springilmiofotoalbum.service;

import com.experis.course.springilmiofotoalbum.model.Category;
import com.experis.course.springilmiofotoalbum.model.Photo;
import com.experis.course.springilmiofotoalbum.repository.CategoryRepository;
import com.experis.course.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PhotoRepository photoRepository;

    public List<Category> getAll() {
        return categoryRepository.findByOrderByName();
    }

    public List<Category> getCategoryList(Optional<String> search) {
        if (search.isPresent()) {
            return categoryRepository.findByNameContainingIgnoreCase(search.get());
        } else {
            return getAll();
        }
    }

    public Category getCategoryById(Integer id) throws RuntimeException {
        Optional<Category> result = categoryRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("Category with id " + id + " not found");
        }
    }

    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException(category.getName());
        }

        category.setName(category.getName().toLowerCase());

        return categoryRepository.save(category);
    }

    public Category editCategory(
            Category category,
            Integer id
    ) throws RuntimeException {
        Category dbCategory = getCategoryById(id);
        dbCategory.setName(category.getName());

        return categoryRepository.save(dbCategory);
    }

    public void deleteCategory(
            Category category,
            Integer id
    ) {
        for (Photo photo : photoRepository.findAll()) {
            if (photo.getCategories().contains(category)) {
                photo.getCategories().remove(category);
                photoRepository.save(photo);
            }
        }

        categoryRepository.deleteById(id);
    }
}
