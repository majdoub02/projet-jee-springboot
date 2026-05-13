import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Permission { id: number; nom: string; description: string; }
export interface Role { id: number; nom: string; description: string; permissions: Permission[]; }

@Injectable({ providedIn: 'root' })
export class RoleService {
  private api = `${environment.apiUrl}/roles`;
  constructor(private http: HttpClient) {}

  getAll(): Observable<Role[]> { return this.http.get<Role[]>(this.api); }
  getPermissions(): Observable<Permission[]> { return this.http.get<Permission[]>(`${this.api}/permissions`); }
  create(nom: string, description: string): Observable<Role> {
    return this.http.post<Role>(this.api, { nom, description });
  }
  addPermission(id: number, permId: number): Observable<Role> {
    return this.http.post<Role>(`${this.api}/${id}/permissions/${permId}`, {});
  }
  removePermission(id: number, permId: number): Observable<Role> {
    return this.http.delete<Role>(`${this.api}/${id}/permissions/${permId}`);
  }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.api}/${id}`); }
}