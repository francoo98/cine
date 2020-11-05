import { Component, OnInit } from '@angular/core';
import { Pelicula } from 'src/app/entidades/Pelicula/Pelicula';
import { PeliculaServicio } from '../PeliculaServicio';

@Component({
  selector: 'app-lista-de-peliculas',
  templateUrl: './lista-de-peliculas.component.html',
  styleUrls: ['./lista-de-peliculas.component.css'],
})
export class ListaDePeliculasComponent implements OnInit {
  peliculas: Pelicula[];

  constructor(private servicio: PeliculaServicio) {}

  ngOnInit(): void {
    this.getPeliculas();
  }

  getPeliculas() {
    this.servicio.getPeliculas().subscribe(peliculas => (this.peliculas = peliculas));
  }
}
