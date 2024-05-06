import { Injectable } from '@angular/core';
import { MessageDto } from '../class/dto/message-dto';
import axios from "axios";
import { RegistroClienteDTO } from '../class/dto/registro-cliente-dto';
import { LoginDto } from '../class/dto/login-dto';
import { response } from 'express';
import { MensajeAuthDto } from '../class/dto/mensaje-auth-dto';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public baseUrl: string = 'http://localhost:8080/api/auth';

  constructor() { }

  registrarUsuario(registroClienteDTO: RegistroClienteDTO) {
    axios.post<MessageDto>(`${this.baseUrl}/register-client`, registroClienteDTO)
      .then((response) => {
        console.log('Usuario registrado exitosamente:', response.data.message);
        // Aquí podrías realizar alguna acción adicional si lo deseas, como redireccionar al usuario a otra página
      })
      .catch((error) => {
        console.error('Error al registrar usuario:', error);
        // Aquí podrías mostrar un mensaje de error al usuario
      });
  }

  loginUsuario(LoginDto: LoginDto) {
    axios.post<MensajeAuthDto>(`${this.baseUrl}/login-client`, LoginDto)
      .then((response) => {
        console.log('Token:', response.data.respuesta);
      })
      .catch((error) => {
        console.log('Error:', error);
      });
  }
}
