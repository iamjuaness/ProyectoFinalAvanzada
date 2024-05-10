import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent {

  @Input() userLoginOn: boolean = false;
  isMenuOpen: boolean = false;
  userPhoto: string = '';
  userName: string = '';
  userEmail: string = '';
  userId: string = '';
  payload: any
  showTooltip: boolean = false;

  constructor(private tokenService: TokenService) {
    
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
    this.showTooltip = !this.showTooltip;
  }

  ngOnInit(): void {
    const token = this.tokenService.getToken();
    if (token) {
      // Si hay un token presente, establecer userLoginOn como true
      this.userLoginOn = true;
      // Decodificar el payload del token y obtener la foto del usuario
      this.payload = this.tokenService.decodePayload(token);
      this.userPhoto = this.payload.photo;
      this.userName = this.payload.nombre;
      this.userEmail = this.payload.sub;
      this.userId = this.payload.id;
    }
  }
  
  logout() {
    this.tokenService.logout();
    this.toggleMenu();
    this.userLoginOn = false;
  }

}
