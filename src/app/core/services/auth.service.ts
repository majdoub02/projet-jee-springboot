import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface LoginRequest { email: string; password: string; }
export interface JwtResponse { token: string; email: string; role: string; }

@Injectable({ providedIn: 'root' })
export class AuthService {
  private api = `${environment.apiUrl}/auth`;
  private tokenKey = 'jwt_token';
  private currentUserSubject = new BehaviorSubject<JwtResponse | null>(this.getStoredUser());
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  login(req: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.api}/login`, req).pipe(
      tap(res => {
        localStorage.setItem(this.tokenKey, res.token);
        localStorage.setItem('current_user', JSON.stringify(res));
        this.currentUserSubject.next(res);
      })
    );
  }

  logout(): void {
    this.http.post(`${this.api}/logout`, {}).subscribe();
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem('current_user');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null { return localStorage.getItem(this.tokenKey); }
  isLoggedIn(): boolean { return !!this.getToken(); }
  getStoredUser(): JwtResponse | null {
    const u = localStorage.getItem('current_user');
    return u ? JSON.parse(u) : null;
  }
}