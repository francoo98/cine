import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CineTestModule } from '../../../test.module';
import { ProyeccionComponent } from 'app/entities/proyeccion/proyeccion.component';
import { ProyeccionService } from 'app/entities/proyeccion/proyeccion.service';
import { Proyeccion } from 'app/shared/model/proyeccion.model';

describe('Component Tests', () => {
  describe('Proyeccion Management Component', () => {
    let comp: ProyeccionComponent;
    let fixture: ComponentFixture<ProyeccionComponent>;
    let service: ProyeccionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CineTestModule],
        declarations: [ProyeccionComponent],
      })
        .overrideTemplate(ProyeccionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProyeccionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProyeccionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Proyeccion(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.proyeccions && comp.proyeccions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
