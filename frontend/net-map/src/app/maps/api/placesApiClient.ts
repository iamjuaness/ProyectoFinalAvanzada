import { Injectable } from "@angular/core";
import axios, { AxiosInstance, AxiosRequestConfig } from "axios";
import { environment } from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class PlacesApiClient {

  private axiosInstance: AxiosInstance;

  public baseUrl: string = 'https://api.mapbox.com/geocoding/v5/mapbox.places';
  

  constructor() {
    // Configura la instancia de Axios con la URL base y cualquier otra configuración que puedas necesitar
    this.axiosInstance = axios.create({
        baseURL: this.baseUrl,
      // Puedes agregar más configuraciones aquí según tus necesidades
    });
  }

  // Método para realizar solicitudes GET con parámetros
  public get<T>(url: string, params?: { proximity: string }) {
    const defaultParams = {
      limit: 5,
      language: 'es',
      access_token: environment.apiKey,
      };

    return this.axiosInstance.get<T>(url, {
      params: { ...defaultParams, ...params },
    });
  }

  
}