import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProyeccion } from 'app/shared/model/proyeccion.model';
import { ProyeccionService } from './proyeccion.service';
import { ProyeccionDeleteDialogComponent } from './proyeccion-delete-dialog.component';

@Component({
  selector: 'jhi-proyeccion',
  templateUrl: './proyeccion.component.html',
})
export class ProyeccionComponent implements OnInit, OnDestroy {
  proyeccions?: IProyeccion[];
  eventSubscriber?: Subscription;

  constructor(protected proyeccionService: ProyeccionService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.proyeccionService.query().subscribe((res: HttpResponse<IProyeccion[]>) => (this.proyeccions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProyeccions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProyeccion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProyeccions(): void {
    this.eventSubscriber = this.eventManager.subscribe('proyeccionListModification', () => this.loadAll());
  }

  delete(proyeccion: IProyeccion): void {
    const modalRef = this.modalService.open(ProyeccionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.proyeccion = proyeccion;
  }
}
