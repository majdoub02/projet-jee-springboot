package com.projet.usermanager.controller;

import com.projet.usermanager.entity.HistoriqueAction;
import com.projet.usermanager.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<Page<HistoriqueAction>> getAll(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(auditService.findAll(page, size));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<HistoriqueAction>> getByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(auditService.findByUser(userId, page, size));
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<Page<HistoriqueAction>> getByAction(
            @PathVariable String action,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(auditService.findByAction(action, page, size));
    }
}