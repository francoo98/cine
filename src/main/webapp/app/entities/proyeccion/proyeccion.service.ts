import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProyeccion } from 'app/shared/model/proyeccion.model';

type EntityResponseType = HttpResponse<IProyeccion>;
type EntityArrayResponseType = HttpResponse<IProyeccion[]>;

@Injectable({ providedIn: 'root' })
export class ProyeccionService {
  public resourceUrl = SERVER_API_URL + 'api/proyeccions';

  constructor(protected http: HttpClient) {}

  create(proyeccion: IProyeccion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proyeccion);
    return this.http
      .post<IProyeccion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(proyeccion: IProyeccion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proyeccion);
    return this.http
      .put<IProyeccion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProyeccion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProyeccion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(proyeccion: IProyeccion): IProyeccion {
    const copy: IProyeccion = Object.assign({}, proyeccion, {
      fechaInicio: proyeccion.fechaInicio && proyeccion.fechaInicio.isValid() ? proyeccion.fechaInicio.format(DATE_FORMAT) : undefined,
      fechaFin: proyeccion.fechaFin && proyeccion.fechaFin.isValid() ? proyeccion.fechaFin.format(DATE_FORMAT) : undefined,
      hora: proyeccion.hora && proyeccion.hora.isValid() ? proyeccion.hora.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaInicio = res.body.fechaInicio ? moment(res.body.fechaInicio) : undefined;
      res.body.fechaFin = res.body.fechaFin ? moment(res.body.fechaFin) : undefined;
      res.body.hora = res.body.hora ? moment(res.body.hora) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((proyeccion: IProyeccion) => {
        proyeccion.fechaInicio = proyeccion.fechaInicio ? moment(proyeccion.fechaInicio) : undefined;
        proyeccion.fechaFin = proyeccion.fechaFin ? moment(proyeccion.fechaFin) : undefined;
        proyeccion.hora = proyeccion.hora ? moment(proyeccion.hora) : undefined;
      });
    }
    return res;
  }
}
