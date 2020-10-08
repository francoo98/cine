import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPelicula, Pelicula } from 'app/shared/model/pelicula.model';
import { PeliculaService } from './pelicula.service';

@Component({
  selector: 'jhi-pelicula-update',
  templateUrl: './pelicula-update.component.html',
})
export class PeliculaUpdateComponent implements OnInit {
  isSaving = false;
  fechaInicioDp: any;
  fechaFinDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descriptcion: [],
    detalle: [],
    duracion: [null, [Validators.min(0), Validators.max(400)]],
    genero: [],
    clasificacion: [],
    estado: [],
    fechaInicio: [],
    fechaFin: [],
  });

  constructor(protected peliculaService: PeliculaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pelicula }) => {
      this.updateForm(pelicula);
    });
  }

  updateForm(pelicula: IPelicula): void {
    this.editForm.patchValue({
      id: pelicula.id,
      nombre: pelicula.nombre,
      descriptcion: pelicula.descriptcion,
      detalle: pelicula.detalle,
      duracion: pelicula.duracion,
      genero: pelicula.genero,
      clasificacion: pelicula.clasificacion,
      estado: pelicula.estado,
      fechaInicio: pelicula.fechaInicio,
      fechaFin: pelicula.fechaFin,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pelicula = this.createFromForm();
    if (pelicula.id !== undefined) {
      this.subscribeToSaveResponse(this.peliculaService.update(pelicula));
    } else {
      this.subscribeToSaveResponse(this.peliculaService.create(pelicula));
    }
  }

  private createFromForm(): IPelicula {
    return {
      ...new Pelicula(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descriptcion: this.editForm.get(['descriptcion'])!.value,
      detalle: this.editForm.get(['detalle'])!.value,
      duracion: this.editForm.get(['duracion'])!.value,
      genero: this.editForm.get(['genero'])!.value,
      clasificacion: this.editForm.get(['clasificacion'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      fechaInicio: this.editForm.get(['fechaInicio'])!.value,
      fechaFin: this.editForm.get(['fechaFin'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPelicula>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
