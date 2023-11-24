package com.experis.course.springilmiofotoalbum.controller;

import com.experis.course.springilmiofotoalbum.model.Category;
import com.experis.course.springilmiofotoalbum.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public String index(
            @RequestParam Optional<String> search,
            Model model
    ) {
        model.addAttribute("categoryList", categoryService.getCategoryList(search));
        return "categories/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "categories/form";
    }

    @PostMapping("/create")
    public String store(
            @Valid @ModelAttribute("category") Category formCategory,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "categories/form";
        }

        try {
            categoryService.createCategory(formCategory);
            redirectAttributes.addFlashAttribute(
                    "message",
                    "Category: " + formCategory.getName() + " created"
            );
            return "redirect:/categories";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Category with name " + e.getMessage() + " already exists"
            );
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Integer id,
            Model model
    ) {
        try {
            model.addAttribute("category", categoryService.getCategoryById(id));
            return "categories/form";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/edit/{id}")
    public String store(
            @PathVariable Integer id,
            @Valid @ModelAttribute("category") Category formCategory,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "categories/form";
        }

        try {
            categoryService.editCategory(formCategory, id);
            redirectAttributes.addFlashAttribute(
                    "message",
                    "Category: " + formCategory.getName() + " modified"
            );
            return "redirect:/categories";

        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        Category deleteCategory = categoryService.getCategoryById(id);
        categoryService.deleteCategory(deleteCategory, id);
        redirectAttributes.addFlashAttribute(
                "message",
                "Ingredient: " + deleteCategory.getName() + " deleted!"
        );
        return "redirect:/categories";
    }
}
