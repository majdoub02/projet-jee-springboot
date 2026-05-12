package com.projet.usermanager.controller;

import com.projet.usermanager.dto.UtilisateurDTO;
import com.projet.usermanager.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService userService;

    @GetMapping
    public ResponseEntity<Page<UtilisateurDTO.Response>> getAll(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false)    String search) {
        return ResponseEntity.ok(userService.findAll(page, size, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO.Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UtilisateurDTO.Response> create(
            @Valid @RequestBody UtilisateurDTO.CreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO.Response> update(
            @PathVariable Long id,
            @RequestBody UtilisateurDTO.UpdateRequest req) {
        return ResponseEntity.ok(userService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<UtilisateurDTO.Response> toggle(@PathVariable Long id) {
        return ResponseEntity.ok(userService.toggle(id));
    }
}