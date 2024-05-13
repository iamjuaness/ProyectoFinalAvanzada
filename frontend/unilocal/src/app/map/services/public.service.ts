import { Injectable } from '@angular/core';
import axios, { AxiosRequestConfig, AxiosResponse } from 'axios';
import { Observable, from } from 'rxjs';
import { MensajeAuthDto } from '../class/dto/mensaje-auth-dto';
import { Ciudad } from '../class/model/ciudad';

@Injectable({
  providedIn: 'root'
})
export class PublicService {

  private publicoURL = "http://localhost:8080/api/public";

  constructor() { }

  public listarCiudades() {
    return axios.get<Ciudad>(`${this.publicoURL}/listar-ciudades`);
  }
  public listarTiposNegocio(){
    return axios.get<Ciudad>(`${this.publicoURL}/listar-tipos-negocio`);
  }
}
