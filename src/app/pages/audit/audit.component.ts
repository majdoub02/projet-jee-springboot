import { Component } from '@angular/core';

@Component({
  selector: 'app-audit',
  standalone: false,
  templateUrl: './audit.html',
  styleUrl: './audit.css'
})
export class AuditComponent {
  displayedColumns = ['date', 'user', 'action', 'details'];
  logs = [
    { date: '2026-05-12 18:00', user: 'Alice Martin', action: 'LOGIN', details: 'Connexion réussie' },
    { date: '2026-05-12 17:45', user: 'Bob Dupont', action: 'UPDATE', details: 'Modification utilisateur #12' },
    { date: '2026-05-12 17:30', user: 'Alice Martin', action: 'DELETE', details: 'Suppression rôle Invité' },
  ];
}