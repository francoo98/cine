import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Pelicula } from 'src/app/entidades/Pelicula';
import { PeliculaServicio } from '../PeliculaServicio';

@Component({
  selector: 'app-editar-pelicula',
  templateUrl: './editar-pelicula.component.html',
  styleUrls: ['./editar-pelicula.component.css'],
})
export class EditarPeliculaComponent implements OnInit {
  pelicula: Pelicula;

  constructor(private service: PeliculaServicio, private ruta: ActivatedRoute) {}

  ngOnInit(): void {
    this.ruta.paramMap.subscribe({
      next: mapa => {
        this.service.getPelicula(+mapa.get('id')).subscribe(pelicula => (this.pelicula = pelicula));
      },
    });
  }
}
