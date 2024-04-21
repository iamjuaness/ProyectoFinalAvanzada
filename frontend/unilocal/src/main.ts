import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import "@angular/compiler";

import Mapboxgl from 'mapbox-gl'; // or "const mapboxgl = require('mapbox-gl');"
 
Mapboxgl.accessToken = 'pk.eyJ1IjoiaWFtanVhbmVzcyIsImEiOiJjbHNqNXZ2emkyaGdqMmxvMGp6cXF6MDN6In0.YIlLM2pI6yi8FNIUknR7QA';


if (!navigator.geolocation) {
  alert("Navegador no soporta la geolocalizacion");
  throw new Error("Navegador no soporta la geolocalizacion");
}

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
