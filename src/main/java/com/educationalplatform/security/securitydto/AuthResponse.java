package com.educationalplatform.security.securitydto;

import com.educationalplatform.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String message;
    private Role role;

    public AuthResponse(String token, String message, Role  role) {
        this.token = token;
        this.message = message;
        this.role=role;
    }
}
