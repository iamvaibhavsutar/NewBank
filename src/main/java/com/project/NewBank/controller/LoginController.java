package com.project.NewBank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.NewBank.Security.request.LoginRequest;
import com.project.NewBank.Security.request.SignupRequest;

@RestController
@RequestMapping("/home")
public class LoginController {
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok("Login Successful");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {
        //TODO: process POST request
        
        return ResponseEntity.ok("Signup Successful");
    }
    
}
