import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProyeccion, Proyeccion } from 'app/shared/model/proyeccion.model';
import { ProyeccionService } from './proyeccion.service';
import { ProyeccionComponent } from './proyeccion.component';
import { ProyeccionDetailComponent } from './proyeccion-detail.component';
import { ProyeccionUpdateComponent } from './proyeccion-update.component';

@Injectable({ providedIn: 'root' })
export class ProyeccionResolve implements Resolve<IProyeccion> {
  constructor(private service: ProyeccionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProyeccion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((proyeccion: HttpResponse<Proyeccion>) => {
          if (proyeccion.body) {
            return of(proyeccion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Proyeccion());
  }
}

export const proyeccionRoute: Routes = [
  {
    path: '',
    component: ProyeccionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cineApp.proyeccion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProyeccionDetailComponent,
    resolve: {
      proyeccion: ProyeccionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cineApp.proyeccion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProyeccionUpdateComponent,
    resolve: {
      proyeccion: ProyeccionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cineApp.proyeccion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProyeccionUpdateComponent,
    resolve: {
      proyeccion: ProyeccionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cineApp.proyeccion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
