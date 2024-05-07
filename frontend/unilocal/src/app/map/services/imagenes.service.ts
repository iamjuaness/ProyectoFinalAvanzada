import { Injectable } from '@angular/core';
import axios from 'axios';
import { MessageDto } from '../class/dto/message-dto';
import { MensajeAuthDto } from '../class/dto/mensaje-auth-dto';

@Injectable({
  providedIn: 'root'
})
export class ImagenesService {


  public baseUrl: string = 'http://localhost:8080/api/imagenes';
  
  constructor() { }

  subirImagen(photo: any) {
    const formData = new FormData();
    formData.append('file', photo);
    return axios.post<MensajeAuthDto>(`${this.baseUrl}/subir`, formData);
  }
}
