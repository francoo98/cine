import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ListaDePeliculasComponent } from '../peliculas/lista-de-peliculas/lista-de-peliculas.component';
import { PeliculaComponent } from '../peliculas/pelicula/pelicula.component';

const rutas: Routes = [{ path: '', component: ListaDePeliculasComponent }];

@NgModule({
  declarations: [],
  imports: [CommonModule, RouterModule.forRoot(rutas)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
