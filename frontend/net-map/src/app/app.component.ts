import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MapscreenComponent } from './maps/screens/mapscreen/mapscreen.component';
import {Cloudinary} from '@cloudinary/url-gen'
import { MainscreenComponent } from './maps/screens/mainscreen/mainscreen.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MainscreenComponent,
    MapscreenComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

    ngOnInit() {
    const cld = new Cloudinary({cloud: {cloudName: 'dsnq0pvey'}});
  }
}
