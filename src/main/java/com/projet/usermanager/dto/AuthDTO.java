package com.projet.usermanager.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

public class AuthDTO {

    @Data
    public static class LoginRequest {
        @Email @NotBlank
        private String email;
        @NotBlank
        private String motDePasse;
    }

    @Data
    public static class JwtResponse {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String nom;
        private String email;
        private String role;

        public JwtResponse(String token, Long id, String nom,
                           String email, String role) {
            this.token = token;
            this.id    = id;
            this.nom   = nom;
            this.email = email;
            this.role  = role;
        }
    }
}