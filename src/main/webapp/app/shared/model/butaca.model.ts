import { Moment } from 'moment';
import { IProyeccion } from 'app/shared/model/proyeccion.model';
import { EstadosButaca } from 'app/shared/model/enumerations/estados-butaca.model';

export interface IButaca {
  id?: number;
  fechaDeVenta?: Moment;
  fila?: number;
  asiento?: number;
  estado?: EstadosButaca;
  proyeccion?: IProyeccion;
}

export class Butaca implements IButaca {
  constructor(
    public id?: number,
    public fechaDeVenta?: Moment,
    public fila?: number,
    public asiento?: number,
    public estado?: EstadosButaca,
    public proyeccion?: IProyeccion
  ) {}
}
