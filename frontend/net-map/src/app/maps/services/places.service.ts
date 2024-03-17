import { Injectable } from '@angular/core';
import { Feature, PlacesResponse } from '../interfaces/places';

import { PlacesApiClient } from '../api';
import { MapService } from './map.service';

@Injectable({
  providedIn: 'root'
})
export class PlacesService {

  public userLocation?: [number, number];

  public isLoadPlaces: boolean = false;
  public places: Feature[] = [];

  
  get isUserLocationReady(): boolean {
    return !!this.userLocation;
  }

  constructor(
    private placesApi: PlacesApiClient,
    private mapService: MapService
  ) {
    this.getUserLocation();
  }

  public async getUserLocation(): Promise<[number, number]> {

    return new Promise((resolve, reject) => {

      navigator.geolocation.getCurrentPosition(
        ({ coords }) => {
          this.userLocation = [coords.longitude, coords.latitude]
          resolve(this.userLocation)
        },
        (err) => {
          alert('No se pudo obtener la geolocalizacion');
          console.log(err);
          reject();
        }
      );
      

    });
  }

  getPlacesByQuery(query: string = '') {

    //todo: evaluar cuando el query es un string vacio
    if (query.length === 0) {
      this.places = [];
      this.isLoadPlaces = false;
      return;
    }



    if (!this.userLocation) throw Error('No hay userLocation');

    this.isLoadPlaces = true;


    this.placesApi.get<PlacesResponse>(`/${query}.json?`, {
        proximity: this.userLocation.join(', ')
    })
      .then(resp => {
        this.isLoadPlaces = false;
        this.places = resp.data.features;

        this.mapService.createMarkerFromPlaces(this.places, this.userLocation!);

      }).catch
      
  }

  deletePlaces() {
    this.places = [];
  }
}
