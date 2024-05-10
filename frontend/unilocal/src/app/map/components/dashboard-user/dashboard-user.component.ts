import { Component } from '@angular/core';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-dashboard-user',
  templateUrl: './dashboard-user.component.html',
  styleUrl: './dashboard-user.component.css'
})
export class DashboardUserComponent {

  userPhoto: string = '';
  userName: string = '';
  userEmail: string = '';
  payload: any

  constructor(private tokenService: TokenService){}

  ngOnInit(): void {
    const token = this.tokenService.getToken();
    if (token) {
      // Decodificar el payload del token y obtener la foto del usuario
      this.payload = this.tokenService.decodePayload(token);
      this.userPhoto = this.payload.photo;
      this.userName = this.payload.nombre;
      this.userEmail = this.payload.sub;
    }
  }

}
