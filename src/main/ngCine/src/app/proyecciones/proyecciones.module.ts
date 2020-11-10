import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListaDeProyeccionesComponent } from './lista-de-proyecciones/lista-de-proyecciones.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [ListaDeProyeccionesComponent],
  imports: [CommonModule, HttpClientModule],
  exports: [ListaDeProyeccionesComponent],
})
export class ProyeccionesModule {}
