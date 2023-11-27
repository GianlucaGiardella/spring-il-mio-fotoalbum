package com.experis.course.springilmiofotoalbum.controller;

import com.experis.course.springilmiofotoalbum.model.User;
import com.experis.course.springilmiofotoalbum.repository.UserRepository;
import com.experis.course.springilmiofotoalbum.security.DatabaseUserDetails;
import com.experis.course.springilmiofotoalbum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public String index(
            Authentication authentication,
            @RequestParam Optional<String> search,
            Model model
    ) {
        DatabaseUserDetails principal = (DatabaseUserDetails) authentication.getPrincipal();
        User loggedUser = userService.getUserById(principal.getId());

        model.addAttribute(loggedUser.getFirstName());
        model.addAttribute(loggedUser.getLastName());

        model.addAttribute("userList", userService.getUsers(search));

        return "/users/list";
    }
}
