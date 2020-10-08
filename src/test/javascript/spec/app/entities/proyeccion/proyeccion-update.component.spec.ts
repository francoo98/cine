import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CineTestModule } from '../../../test.module';
import { ProyeccionUpdateComponent } from 'app/entities/proyeccion/proyeccion-update.component';
import { ProyeccionService } from 'app/entities/proyeccion/proyeccion.service';
import { Proyeccion } from 'app/shared/model/proyeccion.model';

describe('Component Tests', () => {
  describe('Proyeccion Management Update Component', () => {
    let comp: ProyeccionUpdateComponent;
    let fixture: ComponentFixture<ProyeccionUpdateComponent>;
    let service: ProyeccionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CineTestModule],
        declarations: [ProyeccionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProyeccionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProyeccionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProyeccionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Proyeccion(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Proyeccion();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
