package com.projet.usermanager.service;

import com.projet.usermanager.aspect.Auditable;
import com.projet.usermanager.dto.UtilisateurDTO;
import com.projet.usermanager.entity.*;
import com.projet.usermanager.exception.ResourceNotFoundException;
import com.projet.usermanager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public Page<UtilisateurDTO.Response> findAll(int page, int size, String search) {
        var pageable = PageRequest.of(page, size, Sort.by("nom").ascending());
        Page<Utilisateur> result = (search != null && !search.isBlank())
            ? userRepo.search(search, pageable)
            : userRepo.findAll(pageable);
        return result.map(this::toDTO);
    }

    public UtilisateurDTO.Response findById(Long id) {
        return toDTO(getOrThrow(id));
    }

    @Auditable(action = "CREATE_USER")
    @Transactional
    public UtilisateurDTO.Response create(UtilisateurDTO.CreateRequest req) {
        if (userRepo.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email déjà utilisé");

        var user = Utilisateur.builder()
            .nom(req.getNom())
            .email(req.getEmail())
            .motDePasse(encoder.encode(req.getMotDePasse()))
            .actif(true)
            .build();

        if (req.getRoleId() != null)
            user.setRole(roleRepo.findById(req.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Rôle", req.getRoleId())));

        return toDTO(userRepo.save(user));
    }

    @Auditable(action = "UPDATE_USER")
    @Transactional
    public UtilisateurDTO.Response update(Long id, UtilisateurDTO.UpdateRequest req) {
        var user = getOrThrow(id);

        if (req.getNom() != null) user.setNom(req.getNom());
        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getMotDePasse() != null && !req.getMotDePasse().isBlank())
            user.setMotDePasse(encoder.encode(req.getMotDePasse()));
        if (req.getActif() != null) user.setActif(req.getActif());
        if (req.getRoleId() != null)
            user.setRole(roleRepo.findById(req.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Rôle", req.getRoleId())));

        return toDTO(userRepo.save(user));
    }

    @Auditable(action = "DELETE_USER")
    @Transactional
    public void delete(Long id) {
        if (!userRepo.existsById(id))
            throw new ResourceNotFoundException("Utilisateur", id);
        userRepo.deleteById(id);
    }

    @Auditable(action = "TOGGLE_USER")
    @Transactional
    public UtilisateurDTO.Response toggle(Long id) {
        var user = getOrThrow(id);
        user.setActif(!user.isActif());
        return toDTO(userRepo.save(user));
    }

    // ---- Méthodes privées ----

    private Utilisateur getOrThrow(Long id) {
        return userRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id));
    }

    private UtilisateurDTO.Response toDTO(Utilisateur u) {
        var r = new UtilisateurDTO.Response();
        r.setId(u.getId());
        r.setNom(u.getNom());
        r.setEmail(u.getEmail());
        r.setActif(u.isActif());
        if (u.getRole() != null) {
            r.setRole(u.getRole().getNom());
            r.setPermissions(u.getRole().getPermissions()
                .stream().map(Permission::getNom).collect(Collectors.toSet()));
        }
        return r;
    }
}