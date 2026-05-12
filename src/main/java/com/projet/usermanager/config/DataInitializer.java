package com.projet.usermanager.config;

import com.projet.usermanager.entity.*;
import com.projet.usermanager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permRepo;
    private final RoleRepository roleRepo;
    private final UtilisateurRepository userRepo;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {

        // --- Permissions ---
        var create = perm("CREATE_USER", "Créer des utilisateurs");
        var read   = perm("READ_USER",   "Voir les utilisateurs");
        var update = perm("UPDATE_USER", "Modifier des utilisateurs");
        var delete = perm("DELETE_USER", "Supprimer des utilisateurs");
        var assign = perm("ASSIGN_ROLE", "Attribuer des rôles");
        var audit  = perm("VIEW_AUDIT",  "Voir les journaux");
        var manage = perm("MANAGE_ROLES","Gérer les rôles");

        // --- Rôles ---
        var admin   = role("ADMIN",   "Administrateur",
                        Set.of(create,read,update,delete,assign,audit,manage));
        var manager = role("MANAGER", "Gestionnaire",
                        Set.of(read, update, audit));
        var user    = role("USER",    "Utilisateur standard",
                        Set.of(read));
                        role("AUDITOR","Auditeur",
                        Set.of(read, audit));

        // --- Utilisateurs ---
        addUser("Admin Principal",  "admin@app.com",   "Admin123!",   admin);
        addUser("Manager Demo",     "manager@app.com", "Manager123!", manager);
        addUser("Utilisateur Demo", "user@app.com",    "User123!",    user);

        System.out.println("=== Données initialisées ===");
        System.out.println("admin@app.com   / Admin123!");
        System.out.println("manager@app.com / Manager123!");
        System.out.println("user@app.com    / User123!");
    }

    private Permission perm(String nom, String desc) {
        return permRepo.findByNom(nom).orElseGet(() ->
            permRepo.save(Permission.builder().nom(nom).description(desc).build()));
    }

    private Role role(String nom, String desc, Set<Permission> perms) {
        return roleRepo.findByNom(nom).orElseGet(() ->
            roleRepo.save(Role.builder().nom(nom).description(desc).permissions(perms).build()));
    }

    private void addUser(String nom, String email, String pwd, Role role) {
        if (!userRepo.existsByEmail(email))
            userRepo.save(Utilisateur.builder()
                .nom(nom).email(email)
                .motDePasse(encoder.encode(pwd))
                .actif(true).role(role).build());
    }
}