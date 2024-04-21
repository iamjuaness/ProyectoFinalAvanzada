import { Component } from '@angular/core';
import { MapService, PlacesService } from '../../services';
import { Feature } from '../../interfaces/places';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-results',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './search-results.component.html',
  styleUrl: './search-results.component.css'
})
export class SearchResultsComponent {


  public selectedId: string = '';

  constructor(
    private placesService: PlacesService,
    private mapService: MapService
  ) {
    
  }


  get isLoadingPlaces(): boolean {
    return this.placesService.isLoadPlaces;
  }

  get places(): Feature[] {
    return this.placesService.places;
  }

  flyTo(place: Feature) {

    this.selectedId  = place.id;
    
    const [lng, lat] = place.center;

    this.mapService.flyTo([lng, lat]);
  }

  getDirections(place: Feature) {

    if (!this.placesService.userLocation) throw Error('No hay userLocation');

    this.placesService.deletePlaces();
    
    const star = this.placesService.userLocation;
    const end = place.center as [number, number];

    this.mapService.getRouteBetweenPoints( star, end);
    
  }

}
