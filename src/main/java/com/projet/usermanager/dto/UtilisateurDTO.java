package com.projet.usermanager.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Set;

public class UtilisateurDTO {

    @Data
    public static class CreateRequest {
        @NotBlank private String nom;
        @Email @NotBlank private String email;
        @NotBlank private String motDePasse;
        private Long roleId;
    }

    @Data
    public static class UpdateRequest {
        private String nom;
        @Email private String email;
        private String motDePasse;
        private Long roleId;
        private Boolean actif;
    }

    @Data
    public static class Response {
        private Long id;
        private String nom;
        private String email;
        private boolean actif;
        private String role;
        private Set<String> permissions;
    }
}