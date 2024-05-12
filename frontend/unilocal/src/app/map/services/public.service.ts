import { Injectable } from '@angular/core';
import axios, { AxiosRequestConfig, AxiosResponse } from 'axios';
import { Observable, from } from 'rxjs';
import { MensajeAuthDto } from '../class/dto/mensaje-auth-dto';

@Injectable({
  providedIn: 'root'
})
export class PublicService {

  private publicoURL = "http://localhost:8080/api/public";

  constructor() { }

  public listarCiudades(): Observable<AxiosResponse<MensajeAuthDto>> {
    return from(axios.get<MensajeAuthDto>(`${this.publicoURL}/listar-ciudades`));
  }
  public listarTiposNegocio(): Observable<AxiosResponse<MensajeAuthDto>> {
    return from(axios.get<MensajeAuthDto>(`${this.publicoURL}/listar-tipos-negocio`));
  }
}
