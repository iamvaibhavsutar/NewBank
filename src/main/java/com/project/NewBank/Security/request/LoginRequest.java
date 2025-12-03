package com.project.NewBank.Security.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    @Getter @Setter
    String username;
    @NotBlank
    @Size(min = 6, max = 50)
    @Getter @Setter
    String password;
}
