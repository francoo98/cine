export class Pelicula {
  id: number;
  nombre: string;
  descripcion: string;
  detalle: string;
  duracion: number;
  genero: string;
  clasificacion: string;
  estado: boolean;
  fechaInicio: Date;
  fechaFin: Date;

  constructor(
    id: number,
    nombre: string,
    descripcion: string,
    detalle: string,
    duracion: number,
    genero: string,
    clasificacion: string,
    estado: boolean,
    fechaInicio: Date,
    fechaFin: Date
  ) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.detalle = detalle;
    this.duracion = duracion;
    this.genero = genero;
    this.clasificacion = clasificacion;
    this.estado = estado;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
  }
}
