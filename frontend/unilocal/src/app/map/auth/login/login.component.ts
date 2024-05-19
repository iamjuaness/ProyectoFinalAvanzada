import { Component } from '@angular/core';
import { LoginDto } from '../../class/dto/login-dto';
import { AuthService } from '../../services/auth-service.service';
import { response } from 'express';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginDto: LoginDto;

  constructor(private authService: AuthService) {
    this.loginDto = new LoginDto();
  }

  login() {
    this.authService.obtenerUsuarios().then((response) => {
      // Buscar el usuario por correo
      const email = this.loginDto.email;
      const usuario = response.data.find((u: { email: string; }) => u.email === email);
      console.log(usuario)
      if (usuario) {
        // Verificar el rol y llamar al método correspondiente
        if (usuario.role === 'USER') {
          this.authService.loginUsuario(this.loginDto)
        } else if (usuario.role === 'MOD') {
          this.authService.loginMod(this.loginDto);
        } else {
          console.log('Rol no reconocido:', usuario.role);
        }
      } else {
        console.log('Usuario no encontrado con el correo:', email);
      }
    });

  }


}
