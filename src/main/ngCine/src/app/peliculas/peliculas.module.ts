import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListaDePeliculasComponent } from './lista-de-peliculas/lista-de-peliculas.component';
import { PeliculaServicio } from './PeliculaServicio';
import { HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from '../interceptores/authInterceptor';
import { PeliculaComponent } from './pelicula/pelicula.component';
import { AppRoutingModule } from '../app-routing/app-routing.module';
import { RouterModule, Routes } from '@angular/router';
import { peliculasRoutingModule } from './peliculas-routing.module';

const rutas: Routes = [
  { path: 'peliculas', component: ListaDePeliculasComponent },
  { path: 'peliculas/:id', component: PeliculaComponent },
];

@NgModule({
  declarations: [ListaDePeliculasComponent, PeliculaComponent],
  imports: [CommonModule, HttpClientModule, AppRoutingModule, RouterModule.forChild(rutas)],
  exports: [ListaDePeliculasComponent],
  providers: [PeliculaServicio, AuthInterceptor],
})
export class PeliculasModule {}
