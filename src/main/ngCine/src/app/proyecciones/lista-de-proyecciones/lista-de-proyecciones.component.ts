import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Proyeccion } from 'src/app/entidades/Proyeccion';
import { ProyeccionService } from '../proyeccion.service';

@Component({
  selector: 'app-lista-de-proyecciones',
  templateUrl: './lista-de-proyecciones.component.html',
  styleUrls: ['./lista-de-proyecciones.component.css'],
})
export class ListaDeProyeccionesComponent implements OnInit {
  proyecciones: Proyeccion[];
  peliculaId: number;

  constructor(private servicio: ProyeccionService, private ruta: ActivatedRoute) {}

  ngOnInit(): void {
    this.getProyecciones();
  }

  getProyecciones(): void {
    this.ruta.paramMap.subscribe({
      next: mapa => {
        this.servicio.getProyecciones(+mapa.get('id')).subscribe(proyecciones => (this.proyecciones = proyecciones));
      },
    });
  }
}
