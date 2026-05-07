package com.projet.usermanager.repository;

import com.projet.usermanager.entity.Historiqueaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface Historiqueactionrepository extends JpaRepository<Historiqueaction, Long> {
    Page<Historiqueaction> findByUtilisateurId(Long userId, Pageable pageable);
    Page<Historiqueaction> findByAction(String action, Pageable pageable);
    List<Historiqueaction> findByDateBetween(LocalDateTime from, LocalDateTime to);
}