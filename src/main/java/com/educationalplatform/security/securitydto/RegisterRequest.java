package com.educationalplatform.security.securitydto;

import com.educationalplatform.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;

}
