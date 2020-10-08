import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PeliculaService } from 'app/entities/pelicula/pelicula.service';
import { IPelicula, Pelicula } from 'app/shared/model/pelicula.model';

describe('Service Tests', () => {
  describe('Pelicula Service', () => {
    let injector: TestBed;
    let service: PeliculaService;
    let httpMock: HttpTestingController;
    let elemDefault: IPelicula;
    let expectedResult: IPelicula | IPelicula[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PeliculaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Pelicula(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', false, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaInicio: currentDate.format(DATE_FORMAT),
            fechaFin: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Pelicula', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaInicio: currentDate.format(DATE_FORMAT),
            fechaFin: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaInicio: currentDate,
            fechaFin: currentDate,
          },
          returnedFromService
        );

        service.create(new Pelicula()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Pelicula', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            descriptcion: 'BBBBBB',
            detalle: 'BBBBBB',
            duracion: 1,
            genero: 'BBBBBB',
            clasificacion: 'BBBBBB',
            estado: true,
            fechaInicio: currentDate.format(DATE_FORMAT),
            fechaFin: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaInicio: currentDate,
            fechaFin: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Pelicula', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            descriptcion: 'BBBBBB',
            detalle: 'BBBBBB',
            duracion: 1,
            genero: 'BBBBBB',
            clasificacion: 'BBBBBB',
            estado: true,
            fechaInicio: currentDate.format(DATE_FORMAT),
            fechaFin: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaInicio: currentDate,
            fechaFin: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Pelicula', () => {
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
