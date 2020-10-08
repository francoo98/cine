import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'pelicula',
        loadChildren: () => import('./pelicula/pelicula.module').then(m => m.CinePeliculaModule),
      },
      {
        path: 'proyeccion',
        loadChildren: () => import('./proyeccion/proyeccion.module').then(m => m.CineProyeccionModule),
      },
      {
        path: 'sala',
        loadChildren: () => import('./sala/sala.module').then(m => m.CineSalaModule),
      },
      {
        path: 'butaca',
        loadChildren: () => import('./butaca/butaca.module').then(m => m.CineButacaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CineEntityModule {}
