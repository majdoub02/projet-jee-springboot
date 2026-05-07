package com.projet.usermanager.repository;

import com.projet.usermanager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface Rolerepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNom(String nom);
    boolean existsByNom(String nom);
}