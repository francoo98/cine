import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IButaca } from 'app/shared/model/butaca.model';
import { ButacaService } from './butaca.service';

@Component({
  templateUrl: './butaca-delete-dialog.component.html',
})
export class ButacaDeleteDialogComponent {
  butaca?: IButaca;

  constructor(protected butacaService: ButacaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.butacaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('butacaListModification');
      this.activeModal.close();
    });
  }
}
