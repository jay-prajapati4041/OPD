import { Component } from '@angular/core'; // Import Component
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router'; // Import router directives

@Component({ // Component decorator
  selector: 'app-root', // Root selector
  standalone: true, // Mark as standalone
  imports: [RouterOutlet, RouterLink, RouterLinkActive], // Import router directives
  templateUrl: './app.component.html', // Template URL
  styleUrl: './app.component.css' // Style URL
}) // End decorator
export class AppComponent { // App shell component — only handles navigation
  title = 'OPD Management System'; // Application title
} // End AppComponent
