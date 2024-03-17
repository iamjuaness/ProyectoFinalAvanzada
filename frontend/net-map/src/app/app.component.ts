import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MapscreenComponent } from './maps/screens/mapscreen/mapscreen.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MapscreenComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  
}
