import { Injectable } from '@angular/core';
import axios from 'axios';
import { MessageDto } from '../class/dto/message-dto';
import { MensajeAuthDto } from '../class/dto/mensaje-auth-dto';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ImagenesService {
  
  constructor() { }

  subirImagen(photo: any) {
    const formData = new FormData();
    formData.append('file', photo);
    return axios.post<MensajeAuthDto>(`${environment.urlImage}/subir`, formData);
  }
}
