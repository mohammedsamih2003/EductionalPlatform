package com.educationalplatform.security.securitydto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String message;
    private String role;

    public AuthResponse(String token, String message, String role) {
        this.token = token;
        this.message = message;
        this.role=role;
    }
}
