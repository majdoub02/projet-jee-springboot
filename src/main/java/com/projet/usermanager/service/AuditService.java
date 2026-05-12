package com.projet.usermanager.service;

import com.projet.usermanager.entity.HistoriqueAction;
import com.projet.usermanager.repository.HistoriqueActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final HistoriqueActionRepository auditRepo;

    public Page<HistoriqueAction> findAll(int page, int size) {
        return auditRepo.findAll(
            PageRequest.of(page, size, Sort.by("date").descending()));
    }

    public Page<HistoriqueAction> findByUser(Long userId, int page, int size) {
        return auditRepo.findByUtilisateurId(userId,
            PageRequest.of(page, size, Sort.by("date").descending()));
    }

    public Page<HistoriqueAction> findByAction(String action, int page, int size) {
        return auditRepo.findByAction(action,
            PageRequest.of(page, size, Sort.by("date").descending()));
    }
}