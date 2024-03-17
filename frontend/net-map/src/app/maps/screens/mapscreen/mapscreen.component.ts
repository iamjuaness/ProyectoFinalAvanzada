import { Component } from '@angular/core';
import { PlacesService } from '../../services';
import { LoadingComponent } from '../../components/loading/loading.component';
import { MapViewComponent } from '../../components/map-view/map-view.component';
import { CommonModule } from '@angular/common';
import { BtnMyLocationComponent } from '../../components/btn-my-location/btn-my-location.component';
import { SearchBarComponent } from '../../components/search-bar/search-bar.component';

@Component({
  selector: 'app-mapscreen',
  standalone: true,
  imports: [
    LoadingComponent,
    MapViewComponent,
    CommonModule,
    BtnMyLocationComponent,
    SearchBarComponent
  ],
  templateUrl: './mapscreen.component.html',
  styleUrl: './mapscreen.component.css'
})
export class MapscreenComponent {

  constructor(private placesService: PlacesService) { }
  
  get isUserLocationReady() {
    return this.placesService.isUserLocationReady;
  }

}
