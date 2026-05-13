import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface AuditLog {
  id: number;
  action: string;
  details: string;
  dateAction: string;
  utilisateur: { id: number; nom: string; prenom: string; email: string; };
}

@Injectable({ providedIn: 'root' })
export class AuditService {
  private api = `${environment.apiUrl}/audit`;
  constructor(private http: HttpClient) {}

  getAll(page = 0, size = 20): Observable<any> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get(this.api, { params });
  }
  getByUser(userId: number, page = 0, size = 20): Observable<any> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get(`${this.api}/user/${userId}`, { params });
  }
  getByAction(action: string, page = 0, size = 20): Observable<any> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get(`${this.api}/action/${action}`, { params });
  }
}