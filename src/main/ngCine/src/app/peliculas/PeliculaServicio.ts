import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Pelicula } from '../entidades/Pelicula/Pelicula';

@Injectable({
  providedIn: 'root',
})
export class PeliculaServicio {
  constructor(private http: HttpClient) {}

  public getPeliculas(): Observable<Pelicula[]> {
    return this.http.get<Pelicula[]>('http://localhost:8080/api/peliculas');
  }
}
