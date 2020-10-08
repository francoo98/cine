import { EstadosSala } from 'app/shared/model/enumerations/estados-sala.model';

export interface ISala {
  id?: number;
  nombre?: string;
  estado?: EstadosSala;
  filas?: number;
  asientos?: number;
}

export class Sala implements ISala {
  constructor(public id?: number, public nombre?: string, public estado?: EstadosSala, public filas?: number, public asientos?: number) {}
}
