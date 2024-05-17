import { Injectable } from '@angular/core';
import { RegisterDto } from '../class/dto/register-dto';
import axios from 'axios';
import { environment } from '../../environments/environment';
import { Lugar } from '../class/model/lugar';
import { Person } from '../class/model/person';

@Injectable({
  providedIn: 'root'
})
export class ModService {

  constructor() { }

  autorizarLugar(registerDto: RegisterDto, lugarId: string) {
    return axios.post(`${environment.urlMod}/autorizar/${lugarId}`, registerDto)
  }  

  rechazarLugar(lugarId: string) {
    return axios.post(`${environment.urlMod}/rechazar/${lugarId}`)
  }

  obtenerLugaresPendiente() {
    return axios.get<Lugar>(`${environment.urlMod}/lugares/pendientes`)
  }

  obtenerLugaresRechazados() {
    return axios.get<Lugar>(`${environment.urlMod}/lugares/rechazados`)
  }

  obtenerLugaresAutorizados() {
    return axios.get<Lugar>(`${environment.urlMod}/lugares/autorizados`)
  }

  obtenerMod(modId: string) {
    return axios.get<Person>(`${environment.urlMod}/get/${modId}`)
  }

  obtenerRevisiones() {
    return axios.get<Person>(`${environment.urlMod}/revision-history`)
  }

}
