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

  constructor(private tokenService: TokenService) {
    
  }

  ngOnInit(): void{
    this.tokenService.isLoggedIn().subscribe((loggedIn: boolean) => {
      this.userLoginOn = loggedIn; // Actualizar el estado del usuario cuando cambie el estado de inicio de sesión
    });
  }


  logout() {
    this.tokenService.logout();
  }

}
