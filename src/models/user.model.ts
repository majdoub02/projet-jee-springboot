export interface User {
  id?: number;
  nom: string;
  email: string;
  motDePasse?: string;
  actif?: boolean;
  role?: string;
  permissions?: string[];
  roleId?: number;
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}