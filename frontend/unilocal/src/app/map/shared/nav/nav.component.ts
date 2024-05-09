import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent {

  userLoginOn: boolean = false;
  isMenuOpen: boolean = false;
  userPhoto: string = '';

  constructor(private tokenService: TokenService) {
    
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  ngOnInit(): void {
    const token = this.tokenService.getToken();
    console.log(token)
    if (token) {
      // Si hay un token presente, establecer userLoginOn como true
      this.userLoginOn = true;
      // Decodificar el payload del token y obtener la foto del usuario
      const payload = this.tokenService.decodePayload(token);
      this.userPhoto = payload.photo;
    }
  }
  


  logout() {
    this.tokenService.logout();
    this.toggleMenu();
  }

}
