// Define una interfaz para representar un lugar
export interface Place {
  id: number;
  description: string;
  name: string;
  schedules: string[];
  images: string[];
  comments: any[] | null; // Puedes definir una interfaz específica para los comentarios si los tienes
  businessType: string; // O enum si tienes uno
  phones: string[];
  stateBusiness: string; // O enum si tienes uno
  owner: string;
  location: { lng: number; lat: number };
  revisions: any[]; // Puedes definir una interfaz específica para las revisiones si las tienes
}

export interface IconMap {
  [key: string]: string; // Índice de tipo string con valores de tipo string
}