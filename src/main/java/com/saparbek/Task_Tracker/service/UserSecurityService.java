package com.saparbek.Task_Tracker.service;

import com.saparbek.Task_Tracker.model.Role;
import com.saparbek.Task_Tracker.model.User;
import com.saparbek.Task_Tracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityService {
    private final UserRepository userRepository;

    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        String email = getCurrentEmail();
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User with " + email + " not found"));
    }

    public boolean isAdmin() {
        return getCurrentUser().getRole() == Role.ADMIN;
    }

    public String getCurrentEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
