import { Pelicula } from './Pelicula';
import { Sala } from './Sala';

export class Proyeccion {
  id: number;
  fechaInicio: Date;
  fechaFin: Date;
  hora: Date;
  estado: Boolean;
  sala: Sala;
  pelicula: Pelicula;

  constructor(id: number, fechaInicio: Date, fechaFin: Date, hora: Date, estado: boolean) {
    this.id = id;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.hora = hora;
    this.estado = estado;
  }
}
