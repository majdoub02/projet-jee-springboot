import { Component, OnInit } from '@angular/core';
import { UserService } from '../../core/services/user.service';
import { RoleService } from '../../core/services/role.service';
import { AuditService } from '../../core/services/audit.service';
import { forkJoin } from 'rxjs';

@Component({ selector: 'app-dashboard', standalone: false, templateUrl: './dashboard.html', styleUrl: './dashboard.css' })
export class DashboardComponent implements OnInit {
  statCards = [
    { label: 'Total utilisateurs', value: 0, icon: 'people', bg: '#ede9fe', color: '#7c3aed' },
    { label: 'Utilisateurs actifs', value: 0, icon: 'check_circle', bg: '#dcfce7', color: '#16a34a' },
    { label: 'Rôles définis', value: 0, icon: 'admin_panel_settings', bg: '#dbeafe', color: '#2563eb' },
    { label: 'Actions auditées', value: 0, icon: 'history', bg: '#fef9c3', color: '#ca8a04' },
  ];
  recentLogs: any[] = [];
  loading = true;

  constructor(
    private userService: UserService,
    private roleService: RoleService,
    private auditService: AuditService
  ) {}

  ngOnInit() {
    forkJoin({
      users: this.userService.getAll(0, 1000),
      roles: this.roleService.getAll(),
      audit: this.auditService.getAll(0, 5)
    }).subscribe({
      next: (res) => {
        this.statCards[0].value = res.users.totalElements;
        this.statCards[1].value = res.users.content.filter((u: any) => u.actif).length;
        this.statCards[2].value = res.roles.length;
        this.statCards[3].value = res.audit.totalElements;
        this.recentLogs = res.audit.content;
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }

  getActionClass(action: string): string {
    const map: any = { LOGIN: 'badge-info', CREATE: 'badge-success', UPDATE: 'badge-warning', DELETE: 'badge-danger' };
    return map[action] || 'badge-default';
  }
}