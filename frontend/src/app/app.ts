import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { MsalService } from '@azure/msal-angular';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  protected readonly title = signal('frontend');
  protected readonly authResult = signal('');

  constructor(private msalService: MsalService, private http: HttpClient) {}

  ngOnInit() {
    // Se ejecuta al recargar la app tras volver de Microsoft con el login
    this.msalService.instance.handleRedirectPromise().then(result => {
      if (result) {
        this.authResult.set('Login exitoso. JWT: ' + result.accessToken.substring(0, 40) + '...');
      }
    }).catch(err => {
      this.authResult.set('Error de login: ' + err.errorMessage);
    });
  }

  protected login() {
    this.msalService.instance.loginRedirect();
  }

  protected ping() {
    this.http.get('http://localhost:8080/api/secure/ping', { responseType: 'text' }).subscribe({
      next: res => this.authResult.set(res),
      error: err => this.authResult.set(`Error ${err.status}: ${err.message}`)
    });
  }
}