package com.projet.usermanager.service;

import com.projet.usermanager.dto.AuthDTO;
import com.projet.usermanager.entity.Utilisateur;
import com.projet.usermanager.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthDTO.JwtResponse login(AuthDTO.LoginRequest req) {
        var auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                req.getEmail(), req.getMotDePasse()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        Utilisateur user = (Utilisateur) auth.getPrincipal();
        String token = jwtUtil.generateToken(user);
        String role  = user.getRole() != null ? user.getRole().getNom() : "NONE";

        return new AuthDTO.JwtResponse(
            token, user.getId(), user.getNom(), user.getEmail(), role);
    }
}