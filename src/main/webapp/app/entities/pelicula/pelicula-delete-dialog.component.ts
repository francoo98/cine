import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPelicula } from 'app/shared/model/pelicula.model';
import { PeliculaService } from './pelicula.service';

@Component({
  templateUrl: './pelicula-delete-dialog.component.html',
})
export class PeliculaDeleteDialogComponent {
  pelicula?: IPelicula;

  constructor(protected peliculaService: PeliculaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.peliculaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('peliculaListModification');
      this.activeModal.close();
    });
  }
}
