package com.projet.usermanager.service;

import com.projet.usermanager.entity.*;
import com.projet.usermanager.exception.ResourceNotFoundException;
import com.projet.usermanager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepo;
    private final PermissionRepository permRepo;

    public List<Role> findAll() { return roleRepo.findAll(); }

    public List<Permission> findAllPermissions() { return permRepo.findAll(); }

    public Role findById(Long id) {
        return roleRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Rôle", id));
    }

    @Transactional
    public Role create(String nom, String description) {
        if (roleRepo.existsByNom(nom))
            throw new IllegalArgumentException("Rôle déjà existant: " + nom);
        return roleRepo.save(Role.builder()
            .nom(nom).description(description).build());
    }

    @Transactional
    public Role addPermission(Long roleId, Long permId) {
        Role role = findById(roleId);
        Permission perm = permRepo.findById(permId)
            .orElseThrow(() -> new ResourceNotFoundException("Permission", permId));
        role.getPermissions().add(perm);
        return roleRepo.save(role);
    }

    @Transactional
    public Role removePermission(Long roleId, Long permId) {
        Role role = findById(roleId);
        role.getPermissions().removeIf(p -> p.getId().equals(permId));
        return roleRepo.save(role);
    }

    @Transactional
    public void delete(Long id) {
        if (!roleRepo.existsById(id))
            throw new ResourceNotFoundException("Rôle", id);
        roleRepo.deleteById(id);
    }
}