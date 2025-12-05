package com.project.NewBank.Service.Login;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.project.NewBank.Security.Response.LoginResponse;
import com.project.NewBank.Security.Response.SignupResponse;
import com.project.NewBank.Security.request.LoginRequest;
import com.project.NewBank.Security.request.SignupRequest;

@Service
public class LoginService {

    public LoginResponse login(LoginRequest loginRequest) {
        // Assuming you have an AuthenticationManager bean configured
        AuthenticationManager auth= null; 

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        try {
            auth.authenticate(authenticationToken);
        } catch (Exception e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed: " + e.getMessage());
        }
        LoginResponse response = new LoginResponse();

            response.setId(loginRequest.getId());
            response.setUsername(loginRequest.getUsername());
            response.setRoles(loginRequest.getRoles());

            return response;
    }
    
    
    
    public SignupResponse signUp(SignupRequest signupRequest) {
        // Implement your signup logic here
        // For example, save the user to the database
        return ResponseEntity.ok("Signup Successful");
    }
}
