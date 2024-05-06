import { Component } from '@angular/core';
import { LoginDto } from '../../class/dto/login-dto';
import { AuthService } from '../../services/auth-service.service';

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
    this.authService.loginUsuario(this.loginDto)
  }


}
