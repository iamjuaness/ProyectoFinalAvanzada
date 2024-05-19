import { Injectable } from '@angular/core';
import { MessageDto } from '../class/dto/message-dto';
import axios from "axios";
import { RegistroClienteDTO } from '../class/dto/registro-cliente-dto';
import { LoginDto } from '../class/dto/login-dto';
import { MensajeAuthDto } from '../class/dto/mensaje-auth-dto';
import { TokenService } from './token.service';
import { ImagenesService } from './imagenes.service';
import { environment } from '../../environments/environment';
import { TokenDto } from '../class/dto/token-dto';
import { Observable } from 'rxjs';
import { response } from 'express';
import { error } from 'console';

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  constructor(private tokenService: TokenService,
  private imagenService: ImagenesService) { }

  registrarUsuario(registroClienteDTO: RegistroClienteDTO): Promise<any> {
    return new Promise((resolve, reject) => {
      this.imagenService.subirImagen(registroClienteDTO.photo)
        .then((response) => {
          const cloudinaryURL = response.data.respuesta.secure_url;
          registroClienteDTO.photo = cloudinaryURL;

          axios.post<MensajeAuthDto>(`${environment.urlAuth}/register-client`, registroClienteDTO)
            .then((response) => {
              const payload = this.tokenService.decodePayload(response.data.respuesta.token);
              const id = payload.id;
              this.tokenService.signup(response.data.respuesta.token, id);
              resolve(response.data.respuesta); // Resuelve la promesa con los datos de la respuesta
            })
            .catch((error) => {
              console.error('Error al registrar usuario:', error);
              reject(error); // Rechaza la promesa con el error
            });

        })
        .catch((error) => {
          console.error("No se pudo subir la foto", error);
          reject(error); // Rechaza la promesa con el error
        });
    });
  }

  loginUsuario(LoginDto: LoginDto) {
    axios.post<MensajeAuthDto>(`${environment.urlAuth}/login-client`, LoginDto)
      .then((response) => {
        const payload = this.tokenService.decodePayload(response.data.respuesta.token);
        const id = payload.id;
        this.tokenService.loginUser(response.data.respuesta.token, id);
        
      })
      .catch((error) => {
        console.log('Error:', error);
      });
  }

  loginMod(LoginDto: LoginDto) {
    axios.post<MensajeAuthDto>(`${environment.urlAuth}/login-mod`, LoginDto)
      .then((response) => {
        const payload = this.tokenService.decodePayload(response.data.respuesta.token);
        const id = payload.id;
        this.tokenService.loginMod(response.data.respuesta.token, id);
        
      })
      .catch((error) => {
        console.log('Error:', error);
      });
  }


  refresh() {
    return axios.post<MensajeAuthDto>('/refresh', this.tokenService.getToken()).then((response) => {
      this.tokenService.setToken(response.data.respuesta);
    }).catch((error) => {
      console.log('No se pudo actualizar el token:', error);
    });
  }

  obtenerUsuarios() {
    return axios.get(`${environment.urlAuth}/get-all-users`);
  }
}
