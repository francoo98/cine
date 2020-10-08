import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CineSharedModule } from 'app/shared/shared.module';
import { ProyeccionComponent } from './proyeccion.component';
import { ProyeccionDetailComponent } from './proyeccion-detail.component';
import { ProyeccionUpdateComponent } from './proyeccion-update.component';
import { ProyeccionDeleteDialogComponent } from './proyeccion-delete-dialog.component';
import { proyeccionRoute } from './proyeccion.route';

@NgModule({
  imports: [CineSharedModule, RouterModule.forChild(proyeccionRoute)],
  declarations: [ProyeccionComponent, ProyeccionDetailComponent, ProyeccionUpdateComponent, ProyeccionDeleteDialogComponent],
  entryComponents: [ProyeccionDeleteDialogComponent],
})
export class CineProyeccionModule {}
