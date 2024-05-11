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
      this.userPhoto = this.tokenService.getPhoto();
      this.userName = this.tokenService.getNombre();
      this.userEmail = this.tokenService.getEmail();
    }
  }

  showPanel = false;

  openPanel() {
    this.showPanel = true;
  }

  closePanel() {
    this.showPanel = false;
  }

}
