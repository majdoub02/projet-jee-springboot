import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";

export interface AuditLog {
  id: number;
  action: string;
  details: string;
  dateAction: string;
  utilisateur: { id: number; nom: string; prenom: string; email: string; };
}

@Injectable({ providedIn: "root" })
export class AuditService {
  private api = "http://localhost:8080/api/audit";
  constructor(private http: HttpClient) {}
  getAll(page = 0, size = 20): Observable<any> {
    const params = new HttpParams().set("page", page).set("size", size);
    return this.http.get(this.api, { params });
  }
}
