import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CineTestModule } from '../../../test.module';
import { ProyeccionDetailComponent } from 'app/entities/proyeccion/proyeccion-detail.component';
import { Proyeccion } from 'app/shared/model/proyeccion.model';

describe('Component Tests', () => {
  describe('Proyeccion Management Detail Component', () => {
    let comp: ProyeccionDetailComponent;
    let fixture: ComponentFixture<ProyeccionDetailComponent>;
    const route = ({ data: of({ proyeccion: new Proyeccion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CineTestModule],
        declarations: [ProyeccionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProyeccionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProyeccionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load proyeccion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proyeccion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
