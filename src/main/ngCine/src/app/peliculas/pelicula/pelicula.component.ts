import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Pelicula } from 'src/app/entidades/Pelicula';
import { PeliculaServicio } from '../PeliculaServicio';

@Component({
  selector: 'app-pelicula',
  templateUrl: './pelicula.component.html',
  styleUrls: ['./pelicula.component.css'],
})
export class PeliculaComponent implements OnInit {
  pelicula: Pelicula;

  constructor(private servicio: PeliculaServicio, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.getPelicula(id);
  }

  getPelicula(id: number) {
    this.servicio.getPelicula(id).subscribe(pelicula => (this.pelicula = pelicula));
  }
}
