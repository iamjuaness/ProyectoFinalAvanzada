import { Injectable } from "@angular/core";
import axios, { AxiosInstance, AxiosRequestConfig } from "axios";
import { environment } from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DirectionsApiClient {

  private axiosInstance: AxiosInstance;

  public baseUrl: string = 'https://api.mapbox.com/directions/v5/mapbox/driving';
  

  constructor() {
    // Configura la instancia de Axios con la URL base y cualquier otra configuración que puedas necesitar
    this.axiosInstance = axios.create({
        baseURL: this.baseUrl,
      // Puedes agregar más configuraciones aquí según tus necesidades
    });
  }

  // Método para realizar solicitudes GET con parámetros
  public get<T>(url: string) {
    const defaultParams = {
        alternatives: false,
        geometries: 'geojson',
        language: 'es',
        overview: 'simplified',
        steps: false,
        access_token: environment.apiKey,
    };

    return this.axiosInstance.get<T>(url, {
      params: { ...defaultParams},
    });
  }
}