import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor() {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const id_token = localStorage.getItem('id_token');
    if (id_token) {
      const actualizado = req.clone({ headers: req.headers.set('Authorization', 'Bearer ' + id_token) });
      console.log('Auth interceptor!');
      return next.handle(actualizado);
    } else {
      return next.handle(req);
    }
  }
}
