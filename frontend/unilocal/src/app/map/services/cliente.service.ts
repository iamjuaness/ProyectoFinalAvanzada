import { Injectable } from '@angular/core';
import axios, { AxiosRequestConfig } from 'axios';
import { Person } from '../class/model/person';
import { environment } from '../../environments/environment';
import { MessageDto } from '../class/dto/message-dto';
import { Lugar } from '../class/model/lugar';
import { MensajeAuthDto } from '../class/dto/mensaje-auth-dto';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor(private tokenService: TokenService) { }


  getAllPerson() {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.get<Person>(`${environment.urlClient}/get-all`, {headers});
  }

  getPerson(usuarioId: string) {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.get<Person>(`${environment.urlClient}/get/${usuarioId}`, {headers});
  }
  
  editProfile(usuarioId: string) {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.put<MessageDto>(`${environment.urlClient}/edit-profile/${usuarioId}`, {headers});
  }
  
  deleteUser(usuarioId: string) {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.delete<MessageDto>(`${environment.urlClient}/delete/${usuarioId}`, {headers});
  }
  
  agregarFavorito(usuarioId: string, lugarId: number) {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.post(`${environment.urlClient}/${usuarioId}/favoritos/${lugarId}`, {}, {headers});
  }
  
  eliminarFavoritos(usuarioId: string, lugarId: number) {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.delete(`${environment.urlClient}/${usuarioId}/eliminar-favoritos/${lugarId}`, {headers});
  }
  
  obtenerFavoritos(usuarioId: string) {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.get<Lugar>(`${environment.urlClient}/${usuarioId}/favoritos`, {headers});
  }
  
  agregarComentario(lugarId: number) {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.post(`${environment.urlClient}/${lugarId}/comments`, {headers});
  }
  
  obtenerLugaresUsuario(usuarioId: string) {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.get<Lugar>(`${environment.urlClient}/usuario/${usuarioId}/lugares`, {headers});
  }
  
  responderComentario(usuarioId: string) {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.post<MensajeAuthDto>(`${environment.urlClient}/comentario/${usuarioId}/responder`, {headers});
  }
  
  agregarCalificacion(lugarId: number) {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.post<MensajeAuthDto>(`${environment.urlClient}/${lugarId}/qualifications`, {headers});
  }
  
  eliminarComentario(commentId: string, clienteId: string) {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.delete<MensajeAuthDto>(`${environment.urlClient}/comentario/${commentId}/delete/${clienteId}`, {headers});
  }
}
