import { Moment } from 'moment';

export interface IPelicula {
  id?: number;
  nombre?: string;
  descriptcion?: string;
  detalle?: string;
  duracion?: number;
  genero?: string;
  clasificacion?: string;
  estado?: boolean;
  fechaInicio?: Moment;
  fechaFin?: Moment;
}

export class Pelicula implements IPelicula {
  constructor(
    public id?: number,
    public nombre?: string,
    public descriptcion?: string,
    public detalle?: string,
    public duracion?: number,
    public genero?: string,
    public clasificacion?: string,
    public estado?: boolean,
    public fechaInicio?: Moment,
    public fechaFin?: Moment
  ) {
    this.estado = this.estado || false;
  }
}
