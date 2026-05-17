import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { AuditService, AuditLog } from '../../core/services/audit.service';

@Component({ selector: 'app-audit', standalone: false, templateUrl: './audit.html', styleUrl: './audit.css' })
export class AuditComponent implements OnInit {
  logs: AuditLog[] = [];
  totalElements = 0;
  pageSize = 20;
  currentPage = 0;
  loading = false;
  displayedColumns = ['date', 'user', 'action', 'details'];

  constructor(private auditService: AuditService) {}

  ngOnInit() { this.load(); }

  load() {
    this.loading = true;
    this.auditService.getAll(this.currentPage, this.pageSize).subscribe({
      next: (res) => { this.logs = res.content; this.totalElements = res.totalElements; this.loading = false; },
      error: () => this.loading = false
    });
  }

  onPage(event: PageEvent) { this.currentPage = event.pageIndex; this.pageSize = event.pageSize; this.load(); }

  getActionClass(action: string): string {
    const map: any = { LOGIN: 'badge-info', CREATE: 'badge-success', UPDATE: 'badge-warning', DELETE: 'badge-danger', LOGOUT: 'badge-default' };
    return map[action] || 'badge-default';
  }
}