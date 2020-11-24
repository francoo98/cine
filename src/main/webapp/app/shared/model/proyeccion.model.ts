import { Moment } from 'moment';
import { IPelicula } from 'app/shared/model/pelicula.model';
import { ISala } from 'app/shared/model/sala.model';

export interface IProyeccion {
  id?: number;
  fechaInicio?: Moment;
  fechaFin?: Moment;
  hora?: Moment;
  estado?: boolean;
  pelicula?: IPelicula;
  sala?: ISala;
}

export class Proyeccion implements IProyeccion {
  constructor(
    public id?: number,
    public fechaInicio?: Moment,
    public fechaFin?: Moment,
    public hora?: Moment,
    public estado?: boolean,
    public pelicula?: IPelicula,
    public sala?: ISala
  ) {
    this.estado = this.estado || false;
  }
}
