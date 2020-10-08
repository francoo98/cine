import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IButaca } from 'app/shared/model/butaca.model';
import { ButacaService } from './butaca.service';
import { ButacaDeleteDialogComponent } from './butaca-delete-dialog.component';

@Component({
  selector: 'jhi-butaca',
  templateUrl: './butaca.component.html',
})
export class ButacaComponent implements OnInit, OnDestroy {
  butacas?: IButaca[];
  eventSubscriber?: Subscription;

  constructor(protected butacaService: ButacaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.butacaService.query().subscribe((res: HttpResponse<IButaca[]>) => (this.butacas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInButacas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IButaca): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInButacas(): void {
    this.eventSubscriber = this.eventManager.subscribe('butacaListModification', () => this.loadAll());
  }

  delete(butaca: IButaca): void {
    const modalRef = this.modalService.open(ButacaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.butaca = butaca;
  }
}
