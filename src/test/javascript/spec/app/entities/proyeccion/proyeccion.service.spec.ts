import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProyeccionService } from 'app/entities/proyeccion/proyeccion.service';
import { IProyeccion, Proyeccion } from 'app/shared/model/proyeccion.model';

describe('Service Tests', () => {
  describe('Proyeccion Service', () => {
    let injector: TestBed;
    let service: ProyeccionService;
    let httpMock: HttpTestingController;
    let elemDefault: IProyeccion;
    let expectedResult: IProyeccion | IProyeccion[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProyeccionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Proyeccion(0, currentDate, currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaInicio: currentDate.format(DATE_FORMAT),
            fechaFin: currentDate.format(DATE_FORMAT),
            hora: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Proyeccion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaInicio: currentDate.format(DATE_FORMAT),
            fechaFin: currentDate.format(DATE_FORMAT),
            hora: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaInicio: currentDate,
            fechaFin: currentDate,
            hora: currentDate,
          },
          returnedFromService
        );

        service.create(new Proyeccion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Proyeccion', () => {
        const returnedFromService = Object.assign(
          {
            fechaInicio: currentDate.format(DATE_FORMAT),
            fechaFin: currentDate.format(DATE_FORMAT),
            hora: currentDate.format(DATE_FORMAT),
            estado: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaInicio: currentDate,
            fechaFin: currentDate,
            hora: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Proyeccion', () => {
        const returnedFromService = Object.assign(
          {
            fechaInicio: currentDate.format(DATE_FORMAT),
            fechaFin: currentDate.format(DATE_FORMAT),
            hora: currentDate.format(DATE_FORMAT),
            estado: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaInicio: currentDate,
            fechaFin: currentDate,
            hora: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Proyeccion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
