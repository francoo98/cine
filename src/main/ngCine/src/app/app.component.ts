import { Component } from '@angular/core';
import { AuthService } from './authService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'ngCine';

  constructor(private auth: AuthService) {}

  login() {
    this.auth.login();
  }
}
