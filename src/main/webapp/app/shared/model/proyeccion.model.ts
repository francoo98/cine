import { Moment } from 'moment';
import { ISala } from 'app/shared/model/sala.model';
import { IPelicula } from 'app/shared/model/pelicula.model';

export interface IProyeccion {
  id?: number;
  fechaInicio?: Moment;
  fechaFin?: Moment;
  hora?: Moment;
  estado?: boolean;
  sala?: ISala;
  pelicula?: IPelicula;
}

export class Proyeccion implements IProyeccion {
  constructor(
    public id?: number,
    public fechaInicio?: Moment,
    public fechaFin?: Moment,
    public hora?: Moment,
    public estado?: boolean,
    public sala?: ISala,
    public pelicula?: IPelicula
  ) {
    this.estado = this.estado || false;
  }
}
