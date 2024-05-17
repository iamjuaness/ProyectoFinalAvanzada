import { Injectable } from '@angular/core';
import axios from 'axios';
import { Person } from '../class/model/person';
import { environment } from '../../environments/environment';
import { MessageDto } from '../class/dto/message-dto';
import { Lugar } from '../class/model/lugar';
import { MensajeAuthDto } from '../class/dto/mensaje-auth-dto';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor() { }


  getAllPerson() {
    return axios.get<Person>(`${environment.urlClient}/get-all`);
  }

  getPerson(usuarioId : string) {
    return axios.get<Person>(`${environment.urlClient}/get/${usuarioId}`);
  }
  
  editProfile(usuarioId : string) {
    return axios.put<MessageDto>(`${environment.urlClient}/edit-profile/${usuarioId}`);
  }
  
  deleteUser(usuarioId : string) {
    return axios.delete<MessageDto>(`${environment.urlClient}/delete/${usuarioId}`);
  }
  
  agregarFavorito(usuarioId : string, lugarId: string) {
    return axios.post(`${environment.urlClient}/${usuarioId}/favoritos/${lugarId}`);
  }
  
  eliminarFavoritos(usuarioId : string, lugarId: string) {
    return axios.delete(`${environment.urlClient}/${usuarioId}/eliminar-favoritos/${lugarId}`);
  }
  
  obtenerFavoritos(usuarioId : string) {
    return axios.get<Lugar>(`${environment.urlClient}/${usuarioId}/favoritos`);
  }
  
  agregarComentario(lugarId: string) {
    return axios.post(`${environment.urlClient}/${lugarId}/comments`);
  }
  
  obtenerLugaresUsuario(usuarioId: string) {
    return axios.get<Lugar>(`${environment.urlClient}/usuario/${usuarioId}/lugares`);
  }
  
  responderComentario(usuarioId: string) {
    return axios.post<MensajeAuthDto>(`${environment.urlClient}/comentario/${usuarioId}/responder`);
  }
  
  agregarCalificacion(lugarId: string) {
    return axios.post<MensajeAuthDto>(`${environment.urlClient}/${lugarId}/qualifications`);
  }
  
  eliminarComentario(commentId: string, clienteId: string) {
    return axios.delete<MensajeAuthDto>(`${environment.urlClient}/comentario/${commentId}/delete/${clienteId}`);
  }
}
