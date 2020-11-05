import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListaDePeliculasComponent } from './lista-de-peliculas/lista-de-peliculas.component';
import { PeliculaServicio } from './PeliculaServicio';
import { HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from '../interceptores/authInterceptor';

@NgModule({
  declarations: [ListaDePeliculasComponent],
  imports: [CommonModule, HttpClientModule],
  exports: [ListaDePeliculasComponent],
  providers: [PeliculaServicio, AuthInterceptor],
})
export class PeliculasModule {}
