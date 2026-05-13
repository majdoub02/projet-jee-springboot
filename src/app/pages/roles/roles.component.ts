import { Component } from '@angular/core';

@Component({
  selector: 'app-roles',
  standalone: false,
  templateUrl: './roles.html',
  styleUrl: './roles.css'
})
export class RolesComponent {
  displayedColumns = ['name', 'permissions', 'users', 'actions'];
  roles = [
    { name: 'Admin', permissions: ['Lire', 'Écrire', 'Supprimer'], users: 3 },
    { name: 'Éditeur', permissions: ['Lire', 'Écrire'], users: 12 },
    { name: 'Lecteur', permissions: ['Lire'], users: 45 },
  ];
}