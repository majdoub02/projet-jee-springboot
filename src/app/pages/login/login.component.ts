import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {
  form: FormGroup;
  hidePassword = true;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snack: MatSnackBar,
    private http: HttpClient
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      motDePasse: ['', Validators.required]
    });
  }

  login() {
    if (this.form.invalid) return;
    this.loading = true;
    this.http.post<any>('http://localhost:8080/api/auth/login', this.form.value).subscribe({
      next: (res) => {
        localStorage.setItem('jwt_token', res.token);
        localStorage.setItem('current_user', JSON.stringify(res));
        this.loading = false;
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.loading = false;
        this.snack.open(err.error?.message || 'Identifiants incorrects', 'Fermer', { duration: 3000 });
      }
    });
  }
}