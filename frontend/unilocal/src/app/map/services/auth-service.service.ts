import { Injectable } from '@angular/core';
import { MessageDto } from '../class/dto/message-dto';
import axios from "axios";
import { RegistroClienteDTO } from '../class/dto/registro-cliente-dto';
import { LoginDto } from '../class/dto/login-dto';
import { response } from 'express';
import { MensajeAuthDto } from '../class/dto/mensaje-auth-dto';
import { TokenService } from './token.service';
import { ImagenesService } from './imagenes.service';
import { error } from 'console';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public baseUrl: string = 'http://localhost:8080/api/auth';

  constructor(private tokenService: TokenService,
  private imagenService: ImagenesService) { }

  registrarUsuario(registroClienteDTO: RegistroClienteDTO) {
    this.imagenService.subirImagen(registroClienteDTO.photo)
      .then((response) => {
        const cloudinaryURL = response.data.respuesta.secure_url;

        registroClienteDTO.photo = cloudinaryURL


        axios.post<MessageDto>(`${this.baseUrl}/register-client`, registroClienteDTO)
          .then((response) => {
            console.log('Usuario registrado exitosamente:', response.data.message);
            
          })
          .catch((error) => {
            console.error('Error al registrar usuario:', error);
            // Aquí podrías mostrar un mensaje de error al usuario
          });

      })
      .catch((error) => {
        console.error("No se pudo subir la foto", error);
      });

  }

  loginUsuario(LoginDto: LoginDto) {
    axios.post<MensajeAuthDto>(`${this.baseUrl}/login-client`, LoginDto)
      .then((response) => {
        console.log('Token:', response.data.respuesta);
        this.tokenService.login(response.data.respuesta);
      })
      .catch((error) => {
        console.log('Error:', error);
      });
  }
}
