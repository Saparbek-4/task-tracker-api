package com.saparbek.Task_Tracker.service;

import com.saparbek.Task_Tracker.dto.UserRequest;
import com.saparbek.Task_Tracker.model.Role;
import com.saparbek.Task_Tracker.model.Task;
import com.saparbek.Task_Tracker.model.User;
import com.saparbek.Task_Tracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                new EntityNotFoundException("User with id -> " + id + " not found!"));
        userRepository.delete(user);
    }
}
