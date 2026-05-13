import { Component } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';

@Component({ selector: 'app-layout', standalone: false, templateUrl: './layout.html', styleUrl: './layout.css' })
export class LayoutComponent {
  menuItems = [
    { label: 'Dashboard', icon: 'dashboard', route: '/dashboard' },
    { label: 'Utilisateurs', icon: 'people', route: '/users' },
    { label: 'Rôles', icon: 'admin_panel_settings', route: '/roles' },
    { label: 'Audit', icon: 'history', route: '/audit' },
  ];

  constructor(public auth: AuthService) {}
  logout() { this.auth.logout(); }
}