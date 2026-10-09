import { ApplicationConfig } from '@angular/core'; // Import ApplicationConfig
import { provideHttpClient } from '@angular/common/http'; // Import provideHttpClient
import { provideRouter } from '@angular/router'; // Import provideRouter
import { routes } from './app.routes'; // Import app routes

export const appConfig: ApplicationConfig = { // Define appConfig
  providers: [
    provideHttpClient(), // Provide HttpClient globally
    provideRouter(routes) // Provide Router with app routes
  ] // End providers
}; // End appConfig
