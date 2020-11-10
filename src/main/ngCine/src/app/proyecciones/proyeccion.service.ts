import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Proyeccion } from '../entidades/Proyeccion';

@Injectable({
  providedIn: 'root',
})
export class ProyeccionService {
  constructor(private http: HttpClient) {}

  getProyecciones(idPelicula: number) {
    return this.http.get<Proyeccion[]>('http://localhost:8080/api/proyeccions/pelicula/' + idPelicula);
  }
}
