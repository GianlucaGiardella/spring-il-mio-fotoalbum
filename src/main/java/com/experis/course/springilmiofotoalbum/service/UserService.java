package com.experis.course.springilmiofotoalbum.service;

import com.experis.course.springilmiofotoalbum.model.User;
import com.experis.course.springilmiofotoalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getUsers(Optional<String> search) {
        if (search.isPresent()) {
            return userRepository.findByEmailContainingIgnoreCase(search.get());
        } else {
            return userRepository.findAll();
        }
    }

    public User getUserById(Integer id) throws RuntimeException {
        Optional<User> result = userRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("User with id " + id + " not found");
        }
    }
}
