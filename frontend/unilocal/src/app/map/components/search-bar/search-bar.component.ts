import { Component } from '@angular/core';
import { MapService, PlacesService } from '../../services';
import { SearchResultsComponent } from '../search-results/search-results.component';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})
export class SearchBarComponent {

  private debounceTimer?: NodeJS.Timeout;

  constructor(
    private placesService: PlacesService,
    private mapService: MapService
  
  ) { }
  
  onQueryChanged(query: string = '') {
    if (this.debounceTimer) clearTimeout(this.debounceTimer);
    
    if (query.length === 0) {
      this.mapService.removeRouteLayerAndSource();
      this.mapService.removeMarkers();
    }

    this.debounceTimer = setTimeout(() => {

      this.placesService.getPlacesByQuery(query);
      this.mapService.removeRouteLayerAndSource();
    }, 500);
  }

}
