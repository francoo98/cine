import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPelicula } from 'app/shared/model/pelicula.model';
import { PeliculaService } from './pelicula.service';
import { PeliculaDeleteDialogComponent } from './pelicula-delete-dialog.component';

@Component({
  selector: 'jhi-pelicula',
  templateUrl: './pelicula.component.html',
})
export class PeliculaComponent implements OnInit, OnDestroy {
  peliculas?: IPelicula[];
  eventSubscriber?: Subscription;

  constructor(protected peliculaService: PeliculaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.peliculaService.query().subscribe((res: HttpResponse<IPelicula[]>) => (this.peliculas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPeliculas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPelicula): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPeliculas(): void {
    this.eventSubscriber = this.eventManager.subscribe('peliculaListModification', () => this.loadAll());
  }

  delete(pelicula: IPelicula): void {
    const modalRef = this.modalService.open(PeliculaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pelicula = pelicula;
  }
}
