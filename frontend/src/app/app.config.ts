import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideAppInitializer } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi, HTTP_INTERCEPTORS } from '@angular/common/http';
import {
  MsalService, MsalBroadcastService,
  MSAL_INSTANCE, MSAL_GUARD_CONFIG, MSAL_INTERCEPTOR_CONFIG,
  MsalGuardConfiguration, MsalInterceptorConfiguration, MsalInterceptor
} from '@azure/msal-angular';
import { IPublicClientApplication, PublicClientApplication, InteractionType } from '@azure/msal-browser';
import { routes } from './app.routes';

const msalInstance = new PublicClientApplication({
  auth: {
    clientId: '1d082e27-d71f-4d06-a680-bfc8967d31e7',
    authority: 'https://login.microsoftonline.com/f99e2ce1-9cbe-4721-bd63-519d8c893560',
    redirectUri: 'http://localhost:4200/'
  },
  cache: { cacheLocation: 'localStorage' }
});

function protectedResourceMap(): Map<string, Array<string>> {
  const map = new Map<string, Array<string>>();
  map.set('http://localhost:8080/*', ['api://1d082e27-d71f-4d06-a680-bfc8967d31e7/.default']);
  return map;
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    provideAppInitializer(() => msalInstance.initialize()), // clave: init async antes de usar MSAL
    { provide: MSAL_INSTANCE, useValue: msalInstance },
    { provide: MSAL_GUARD_CONFIG, useValue: { interactionType: InteractionType.Redirect } as MsalGuardConfiguration },
    { provide: MSAL_INTERCEPTOR_CONFIG, useValue: { interactionType: InteractionType.Redirect, protectedResourceMap: protectedResourceMap() } as MsalInterceptorConfiguration },
    { provide: HTTP_INTERCEPTORS, useClass: MsalInterceptor, multi: true },
    MsalService,
    MsalBroadcastService
  ]
};