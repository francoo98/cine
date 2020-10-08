import { Moment } from 'moment';
import { IProyeccion } from 'app/shared/model/proyeccion.model';

export interface IButaca {
  id?: number;
  fechaDeVenta?: Moment;
  fila?: number;
  asiento?: number;
  proyeccion?: IProyeccion;
}

export class Butaca implements IButaca {
  constructor(
    public id?: number,
    public fechaDeVenta?: Moment,
    public fila?: number,
    public asiento?: number,
    public proyeccion?: IProyeccion
  ) {}
}
