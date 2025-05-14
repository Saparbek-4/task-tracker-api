package com.saparbek.Task_Tracker.controller;

import com.saparbek.Task_Tracker.model.User;
import com.saparbek.Task_Tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Accessible only to admins")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @Operation(summary = "Delete user by ID", description = "Accessible only to admins")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable
                                 Long id) {
        log.info("User with ID {} deleted by admin", id);
        userService.deleteUserById(id);

        return ResponseEntity.ok("User with ID " + id + " deleted successfully!");
    }

}
