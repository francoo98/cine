import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListaDePeliculasComponent } from './lista-de-peliculas/lista-de-peliculas.component';
import { PeliculaComponent } from './pelicula/pelicula.component';

const rutas: Routes = [
  { path: 'peliculas/', component: ListaDePeliculasComponent },
  { path: 'peliculas/:id', component: PeliculaComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(rutas), CommonModule],
  exports: [RouterModule],
})
export class peliculasRoutingModule {}
