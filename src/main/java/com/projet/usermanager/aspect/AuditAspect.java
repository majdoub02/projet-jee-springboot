package com.projet.usermanager.aspect;

import com.projet.usermanager.entity.HistoriqueAction;
import com.projet.usermanager.repository.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final HistoriqueActionRepository auditRepo;
    private final UtilisateurRepository userRepo;

    @AfterReturning("@annotation(auditable)")
    public void log(JoinPoint jp, Auditable auditable) {
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) return;

            var utilisateur = userRepo.findByEmail(auth.getName()).orElse(null);

            String cible = jp.getArgs().length > 0
                ? jp.getArgs()[0].toString() : null;

            auditRepo.save(HistoriqueAction.builder()
                .utilisateur(utilisateur)
                .action(auditable.action())
                .entiteCible(cible)
                .date(LocalDateTime.now())
                .build());

        } catch (Exception e) {
            System.err.println("Audit error: " + e.getMessage());
        }
    }
}