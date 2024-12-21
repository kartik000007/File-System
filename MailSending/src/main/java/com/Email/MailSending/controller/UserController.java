package com.Email.MailSending.controller;


import com.Email.MailSending.dto.LoginDTO;
import com.Email.MailSending.dto.RegistrationDTO;
import com.Email.MailSending.dto.ResponseDTO;
import com.Email.MailSending.dto.UserSearchDTO;
import com.Email.MailSending.module.Users;
import com.Email.MailSending.service.EmailService;
import com.Email.MailSending.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@Validated @RequestBody RegistrationDTO registrationDTO) {
        try {
            // Register the user
            userService.registerUser(registrationDTO);

            // Send welcome email
            Users registeredUser = userService.getUserByEmail(registrationDTO.getEmail());
            emailService.sendWelcomeEmail(registeredUser);

            return new ResponseEntity<>(new ResponseDTO("User registered successfully, welcome email sent.", "Success"), HttpStatus.CREATED);
        } catch (MessagingException | IOException ex) {
            return new ResponseEntity<>(new ResponseDTO("Failed to send welcome email: " + ex.getMessage(), "Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseDTO("User registration failed: " + ex.getMessage(), "Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@Validated @RequestBody LoginDTO loginDTO) {
        boolean isLoggedIn = userService.loginUser(loginDTO);
        if (isLoggedIn) {
            return new ResponseEntity<>(new ResponseDTO("Login successful, check your phone for a message", "Success"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO("Invalid username or password", "Failure"), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/search")
    public Page<UserSearchDTO> getUser(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection
    ){
        return userService.getUsers(name,page,size,sortField,sortDirection);
    }
}
