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
    this.initializeUserLocation();
    
  }

  private initializeUserLocation(): void {
    if (typeof navigator !== 'undefined' && 'geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(
        ({ coords }) => {
          this.userLocation = [coords.longitude, coords.latitude]
        },
        (err) => {
          console.error('Error al obtener la geolocalización:', err);
        }
      );
    } else {
      console.error('El objeto navigator no está disponible');
      // Aquí podrías proporcionar una implementación alternativa o manejar el error de otra manera
    }
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

  async getPlacesByQuery(query: string = '') {
    if (query.length === 0) {
      this.places = [];
      this.isLoadPlaces = false;
      return;
    }

    if (!this.userLocation) throw Error('No hay userLocation');
    
    const mapServicePlaces = this.mapService.lugares || [];

    // Filtrar lugares que coincidan con el query
    const filteredPlaces = mapServicePlaces.filter(place =>
      place.text.toLowerCase().includes(query.toLowerCase())
    );

    // Realizar la llamada a la API para obtener más lugares
    try {
      const resp = await this.placesApi.get<PlacesResponse>(`/${query}.json?`, {
        proximity: this.userLocation.join(', ')
      });

      const apiPlaces = resp.data.features;

      // Filtrar lugares de la API que estén dentro del radio de 30km
      const filteredApiPlaces = apiPlaces.filter(place =>
        this.calculateDistanceFromUser(place) <= 50
      );

      // Combinar lugares del servicio de mapa y de la API
      this.places = [...filteredPlaces, ...filteredApiPlaces];

      // Ordenar los lugares según su proximidad a la ubicación del usuario
      this.places.sort((a, b) => {
        const distanceToA = this.calculateDistance(a);
        const distanceToB = this.calculateDistance(b);
        const distanceToUserA = this.calculateDistanceFromUser(a);
        const distanceToUserB = this.calculateDistanceFromUser(b);
        
        // Ordena por proximidad a 'a' primero, luego por proximidad al usuario
        return distanceToA - distanceToB || distanceToUserA - distanceToUserB;
      });

      // Crear marcadores en el mapa para los lugares encontrados
      this.mapService.createMarkerFromPlaces(this.places, this.userLocation);

      // Indicar que ya no se están cargando los lugares
      this.isLoadPlaces = false;
    } catch (error) {
      console.error('Error al obtener lugares:', error);
      this.isLoadPlaces = false;
    }
  }



  // Función para calcular la distancia entre dos puntos utilizando la fórmula de Haversine
  private calculateDistance(place: Feature): number {

    if (!this.userLocation) throw Error('No hay userLocation');
    
    const R = 6371; // Radio de la Tierra en kilómetros
    const lat1 = this.userLocation[1];
    const lon1 = this.userLocation[0];
    const lat2 = place.center[1]; // Supongamos que cada lugar tiene una propiedad 'latitud' y 'longitud'
    const lon2 = place.center[0];
    const dLat = this.deg2rad(lat2 - lat1);
    const dLon = this.deg2rad(lon2 - lon1);
    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(this.deg2rad(lat1)) * Math.cos(this.deg2rad(lat2)) *
      Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const d = R * c; // Distancia en kilómetros
    return d;
  }

  // Función auxiliar para convertir grados a radianes
  private deg2rad(deg: number): number {
    return deg * (Math.PI / 180);
  }

  // Función para calcular la distancia entre un lugar y la ubicación del usuario
  private calculateDistanceFromUser(place: Feature): number {
    return this.calculateDistance(place);
  }

  deletePlaces() {
    this.places = [];
  }
}
