package com.project.NewBank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.NewBank.Security.request.LoginRequest;
import com.project.NewBank.Security.request.SignupRequest;
import com.project.NewBank.Service.Login.LoginService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    
    @Autowired
    LoginService loginService;
    
    @PostMapping({"/login", "/api/auth/login"})
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest loginRequest) {
        System.out.println("LOGIN CONTROLLER HIT");
        return ResponseEntity.ok(loginService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Validated SignupRequest signupRequest) {
       
        return ResponseEntity.created(null).body(loginService.signUp(signupRequest));
    }
    
}
