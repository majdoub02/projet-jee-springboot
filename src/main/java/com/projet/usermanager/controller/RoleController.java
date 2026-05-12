package com.projet.usermanager.controller;

import com.projet.usermanager.entity.*;
import com.projet.usermanager.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>> getPermissions() {
        return ResponseEntity.ok(roleService.findAllPermissions());
    }

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody Map<String, String> body) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(roleService.create(body.get("nom"), body.get("description")));
    }

    @PostMapping("/{id}/permissions/{permId}")
    public ResponseEntity<Role> addPerm(
            @PathVariable Long id, @PathVariable Long permId) {
        return ResponseEntity.ok(roleService.addPermission(id, permId));
    }

    @DeleteMapping("/{id}/permissions/{permId}")
    public ResponseEntity<Role> removePerm(
            @PathVariable Long id, @PathVariable Long permId) {
        return ResponseEntity.ok(roleService.removePermission(id, permId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}