import { Injectable } from '@angular/core';
import axios, { AxiosRequestConfig, AxiosResponse } from 'axios';
import { Observable, from } from 'rxjs';
import { MensajeAuthDto } from '../class/dto/mensaje-auth-dto';
import { Ciudad } from '../class/model/ciudad';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PublicService {


  constructor() { }

  public listarCiudades() {
    return axios.get<Ciudad>(`${environment.urlPublic}/listar-ciudades`);
  }
  public listarTiposNegocio(){
    return axios.get<Ciudad>(`${environment.urlPublic}/listar-tipos-negocio`);
  }
}
