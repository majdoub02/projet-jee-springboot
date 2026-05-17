export interface HistoriqueAction {
  id: number;
  utilisateur?: {
    id: number;
    nom: string;
    email: string;
  };
  action: string;
  entiteCible?: string;
  date: string;
}