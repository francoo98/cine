import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProyeccion } from 'app/shared/model/proyeccion.model';
import { ProyeccionService } from './proyeccion.service';

@Component({
  templateUrl: './proyeccion-delete-dialog.component.html',
})
export class ProyeccionDeleteDialogComponent {
  proyeccion?: IProyeccion;

  constructor(
    protected proyeccionService: ProyeccionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.proyeccionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('proyeccionListModification');
      this.activeModal.close();
    });
  }
}
