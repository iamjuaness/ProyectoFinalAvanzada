import { Component, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { MapService, PlacesService } from '../../services';
import mapboxgl, { Marker, Popup } from 'mapbox-gl';


//  We need to load the CSS asset for MapboxGL otherwise it won't work.
@Component({
  selector: 'app-map-view',
  standalone: true,
  imports: [],
  templateUrl: './map-view.component.html',
  styleUrl: './map-view.component.css'
})
export class MapViewComponent implements AfterViewInit {

  // Se declara  el elemento del DOM que va a contener al mapa
  @ViewChild('mapDiv')
    mapDivElement!: ElementRef

  //  Variables para trabajar con el mapa y sus características
  constructor(
    private placesService: PlacesService,
    private mapService: MapService
  ) { }

  //  Inicialización del componente después de cargado en la vista
  ngAfterViewInit(): void {


    // 
    if (!this.placesService.userLocation) throw Error('No hay placesService.userLocation')
    

    // Se le dan las caracteristicas al mapa
    const map = new mapboxgl.Map({
	    container: this.mapDivElement.nativeElement,
	    style: 'mapbox://styles/mapbox/outdoors-v11', // style URL
	    center: this.placesService.userLocation, // starting position [lng, lat]
	    zoom: 16.6, // starting zoom
    });

    // clase popup
    const popup = new Popup()
      .setHTML(`
        <h6>Aqui estoy</h6>
        <span>${this.placesService.userLocation}</span>
      `);
    
    // Definir un Marker
    new Marker({ color: 'gray' })
      .setLngLat(this.placesService.userLocation)
      .setPopup(popup)
      .addTo(map);
    
    this.mapService.setMap(map);
    

  }
  


}
