import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IButaca, Butaca } from 'app/shared/model/butaca.model';
import { ButacaService } from './butaca.service';
import { ButacaComponent } from './butaca.component';
import { ButacaDetailComponent } from './butaca-detail.component';
import { ButacaUpdateComponent } from './butaca-update.component';

@Injectable({ providedIn: 'root' })
export class ButacaResolve implements Resolve<IButaca> {
  constructor(private service: ButacaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IButaca> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((butaca: HttpResponse<Butaca>) => {
          if (butaca.body) {
            return of(butaca.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Butaca());
  }
}

export const butacaRoute: Routes = [
  {
    path: '',
    component: ButacaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cineApp.butaca.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ButacaDetailComponent,
    resolve: {
      butaca: ButacaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cineApp.butaca.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ButacaUpdateComponent,
    resolve: {
      butaca: ButacaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cineApp.butaca.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ButacaUpdateComponent,
    resolve: {
      butaca: ButacaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'cineApp.butaca.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
