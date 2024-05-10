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
  payload: any

  constructor(private tokenService: TokenService) {
    
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  ngOnInit(): void {
    const token = this.tokenService.getToken();
    if (token) {
      // Si hay un token presente, establecer userLoginOn como true
      this.userLoginOn = true;
      // Decodificar el payload del token y obtener la foto del usuario
      this.payload = this.tokenService.decodePayload(token);
      this.userPhoto = this.payload.photo;
    }
  }
  
  logout() {
    this.tokenService.logout();
    this.toggleMenu();
    this.userLoginOn = false;
  }

}
