package com.projet.usermanager.repository;

import com.projet.usermanager.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface Permissionrepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByNom(String nom);
}