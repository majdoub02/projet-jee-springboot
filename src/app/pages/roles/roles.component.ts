import { Component, OnInit } from '@angular/core';
import { RoleService, Role, Permission } from '../../core/services/role.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({ selector: 'app-roles', standalone: false, templateUrl: './roles.html', styleUrl: './roles.css' })
export class RolesComponent implements OnInit {
  displayedColumns = ['nom', 'description', 'permissions', 'actions'];
  roles: Role[] = [];
  permissions: Permission[] = [];
  loading = false;
  showForm = false;
  editingRole: Role | null = null;
  form: FormGroup;

  constructor(
    private roleService: RoleService,
    private fb: FormBuilder,
    private snack: MatSnackBar
  ) {
    this.form = this.fb.group({
      nom: ['', Validators.required],
      description: ['']
    });
  }

  ngOnInit() {
    this.loadRoles();
    this.roleService.getPermissions().subscribe(p => this.permissions = p);
  }

  loadRoles() {
    this.loading = true;
    this.roleService.getAll().subscribe({
      next: (res) => { this.roles = res; this.loading = false; },
      error: () => this.loading = false
    });
  }

  openCreate() { this.editingRole = null; this.form.reset(); this.showForm = true; }

  save() {
    if (this.form.invalid) return;
    this.roleService.create(this.form.value.nom, this.form.value.description).subscribe({
      next: () => { this.snack.open('Rôle créé', '', { duration: 2000 }); this.showForm = false; this.loadRoles(); },
      error: (err) => this.snack.open(err.error?.message || 'Erreur', '', { duration: 3000 })
    });
  }

  delete(role: Role) {
    if (!confirm(`Supprimer le rôle ${role.nom} ?`)) return;
    this.roleService.delete(role.id).subscribe({
      next: () => { this.snack.open('Rôle supprimé', '', { duration: 2000 }); this.loadRoles(); },
      error: (err) => this.snack.open(err.error?.message || 'Erreur', '', { duration: 3000 })
    });
  }
}