import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { UserService, User } from '../../core/services/user.service';
import { RoleService, Role } from '../../core/services/role.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({ selector: 'app-users', standalone: false, templateUrl: './users.html', styleUrl: './users.css' })
export class UsersComponent implements OnInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  users: User[] = [];
  roles: Role[] = [];
  totalElements = 0;
  pageSize = 10;
  currentPage = 0;
  loading = false;
  searchTerm = '';
  showForm = false;
  editingUser: User | null = null;
  displayedColumns = ['avatar', 'nom', 'email', 'role', 'status', 'actions'];

  form: FormGroup;

  constructor(
    private userService: UserService,
    private roleService: RoleService,
    private snack: MatSnackBar,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
  nom: ['', Validators.required],
  prenom: ['', Validators.required],
  email: ['', [Validators.required, Validators.email]],
  motDePasse: [''],  // ✅ motDePasse au lieu de password
  roleId: ['', Validators.required]
});
  }

  ngOnInit() {
    this.loadUsers();
    this.roleService.getAll().subscribe(r => this.roles = r);
  }

  loadUsers() {
    this.loading = true;
    this.userService.getAll(this.currentPage, this.pageSize, this.searchTerm).subscribe({
      next: (res) => { this.users = res.content; this.totalElements = res.totalElements; this.loading = false; },
      error: () => this.loading = false
    });
  }

  onPage(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadUsers();
  }

openCreate() { 
  this.editingUser = null; 
  this.form.reset(); 
  this.form.get('motDePasse')?.setValidators(Validators.required); // ✅
  this.showForm = true; 
}

  openEdit(user: User) {
    this.editingUser = user;
    this.form.patchValue({ nom: user.nom, prenom: user.prenom, email: user.email, roleId: user.role?.id });
    this.form.get('password')?.clearValidators();
    this.showForm = true;
  }

  save() {
    if (this.form.invalid) return;
    const data = this.form.value;
    const obs = this.editingUser
      ? this.userService.update(this.editingUser.id, data)
      : this.userService.create(data);

    obs.subscribe({
      next: () => { this.snack.open('Sauvegardé avec succès', '', { duration: 2000 }); this.showForm = false; this.loadUsers(); },
      error: (err) => this.snack.open(err.error?.message || 'Erreur', '', { duration: 3000 })
    });
  }

  toggle(user: User) {
    this.userService.toggle(user.id).subscribe({
      next: () => { this.snack.open(`Utilisateur ${user.actif ? 'désactivé' : 'activé'}`, '', { duration: 2000 }); this.loadUsers(); }
    });
  }

  delete(user: User) {
    if (!confirm(`Supprimer ${user.prenom} ${user.nom} ?`)) return;
    this.userService.delete(user.id).subscribe({
      next: () => { this.snack.open('Supprimé', '', { duration: 2000 }); this.loadUsers(); }
    });
  }

  getInitials(user: User): string {
    return `${user.prenom?.[0] || ''}${user.nom?.[0] || ''}`.toUpperCase();
  }
}