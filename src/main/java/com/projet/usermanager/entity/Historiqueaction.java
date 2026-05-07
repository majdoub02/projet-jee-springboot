package com.projet.usermanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historique_actions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Historiqueaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @Column(nullable = false)
    private String action;

    private String entiteCible;

    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();
}