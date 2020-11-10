import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaDeProyeccionesComponent } from './lista-de-proyecciones.component';

describe('ListaDeProyeccionesComponent', () => {
  let component: ListaDeProyeccionesComponent;
  let fixture: ComponentFixture<ListaDeProyeccionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListaDeProyeccionesComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaDeProyeccionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
