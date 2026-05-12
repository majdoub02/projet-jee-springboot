package com.projet.usermanager.repository;

import com.projet.usermanager.entity.HistoriqueAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoriqueActionRepository extends JpaRepository<HistoriqueAction, Long> {
    Page<HistoriqueAction> findByUtilisateurId(Long userId, Pageable pageable);
    Page<HistoriqueAction> findByAction(String action, Pageable pageable);
    List<HistoriqueAction> findByDateBetween(LocalDateTime from, LocalDateTime to);
}