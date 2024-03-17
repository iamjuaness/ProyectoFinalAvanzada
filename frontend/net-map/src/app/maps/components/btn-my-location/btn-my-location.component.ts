import { Component } from '@angular/core';
import { MapService, PlacesService } from '../../services';

@Component({
  selector: 'app-btn-my-location',
  standalone: true,
  imports: [],
  templateUrl: './btn-my-location.component.html',
  styleUrl: './btn-my-location.component.css'
})
export class BtnMyLocationComponent {

  constructor(
    private placesService: PlacesService,
    private mapServices: MapService
  ) { }
  

  // metodo para volver a mi ubicacion
  goToMyLocation() {

    if (!this.placesService.isUserLocationReady) throw Error('No hay ubicacion de usuario')
    if(!this.mapServices.isMapReady) throw Error('El mapa no esta listo');
    
    this.mapServices.flyTo(this.placesService.userLocation!)

  }

}
