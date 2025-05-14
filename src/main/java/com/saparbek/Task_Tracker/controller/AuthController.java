package com.saparbek.Task_Tracker.controller;

import com.saparbek.Task_Tracker.dto.AuthRequest;
import com.saparbek.Task_Tracker.dto.AuthResponse;
import com.saparbek.Task_Tracker.dto.RegisterRequest;
import com.saparbek.Task_Tracker.model.Role;
import com.saparbek.Task_Tracker.model.User;
import com.saparbek.Task_Tracker.repository.UserRepository;
import com.saparbek.Task_Tracker.security.CustomUserDetails;
import com.saparbek.Task_Tracker.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Role DEFAULT_ROLE = Role.USER;
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);


    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Register new user", description = "Registers a new user with default USER role and returns JWT token")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {
        log.info("Registering user with email: {}", request.getEmail());
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthResponse("Email already exosts"));
        }

        User user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                DEFAULT_ROLE
        );
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }

    @Operation(summary = "Login user", description = "Authenticates user and returns JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request) {
        log.info("User '{}' attempting to log in", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }

}
