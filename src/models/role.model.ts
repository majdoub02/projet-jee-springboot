export interface Permission {
  id: number;
  nom: string;
  description: string;
}

export interface Role {
  id: number;
  nom: string;
  description: string;
  permissions: Permission[];
}