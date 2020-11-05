import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login() {
    const payload = '{"username": "admin", "password": "admin"}';
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    this.http.post('http://localhost:8080/api/authenticate', payload, options).subscribe(res => this.setSession(res));
  }

  private setSession(res) {
    console.log(res.id_token);
    localStorage.setItem('id_token', res.id_token);
  }
}
