import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { PeliculasModule } from './peliculas/peliculas.module';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from './authService';
import { httpInterceptorProviders } from './interceptores/index';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { ProyeccionesModule } from './proyecciones/proyecciones.module';

@NgModule({
  declarations: [AppComponent],
  providers: [AuthService, httpInterceptorProviders],
  bootstrap: [AppComponent],
  imports: [BrowserModule, HttpClientModule, PeliculasModule, AppRoutingModule],
})
export class AppModule {}
