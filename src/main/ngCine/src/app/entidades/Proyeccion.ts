import { Pelicula } from './Pelicula';

export class Proyeccion {
  id: number;
  fechaInicio: Date;
  fechaFin: Date;
  hora: Date;
  estado: Boolean;
  sala: number;
  pelicula: Pelicula;

  constructor(id: number, fechaInicio: Date, fechaFin: Date, hora: Date, estado: boolean, sala: number, pelicula: Pelicula) {
    this.id = id;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.hora = hora;
    this.estado = estado;
    this.sala = sala;
    this.pelicula = pelicula;
  }
}
