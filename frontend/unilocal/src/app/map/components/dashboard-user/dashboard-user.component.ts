import { Component } from '@angular/core';
import { TokenService } from '../../services/token.service';
import { ClienteService } from '../../services/cliente.service';
import { Lugar } from '../../class/model/lugar';

@Component({
  selector: 'app-dashboard-user',
  templateUrl: './dashboard-user.component.html',
  styleUrl: './dashboard-user.component.css'
})
export class DashboardUserComponent {

  userPhoto: string = '';
  userName: string = '';
  userEmail: string = '';
  userNickname: string = '';
  userId: string = '';
  payload: any;
  lugaresUsuario!: Lugar[];
  favoritosUsuario!: Lugar[];

  constructor(private tokenService: TokenService, private clientService: ClienteService){}

  ngOnInit(): void {
    const token = this.tokenService.getToken();
    if (token) {
      // Decodificar el payload del token y obtener la foto del usuario
      this.userPhoto = this.tokenService.getPhoto();
      this.userName = this.tokenService.getNombre();
      this.userEmail = this.tokenService.getEmail();
      this.userId = this.tokenService.getCodigo();
      this.obtenerNicknameUsuario();
      this.obtenerLugaresUsuario();
      this.obtenerFavoritos()
    }
  }

  showNegocios = true;
  showFavoritos = false;
  showCrearNegocio = false;

  openCrearNegocio() {
    this.showCrearNegocio = true;
    this.showNegocios = false;
    this.showFavoritos = false;
  }

  openMisNegocios() {
    this.showCrearNegocio = false;
    this.showNegocios = true;
    this.showFavoritos = false;
  }
  openMisFavoritos() {
    this.showCrearNegocio = false;
    this.showNegocios = false;
    this.showFavoritos = true;
  }

  obtenerNicknameUsuario() {
    this.clientService.getPerson(this.userId).then((response) => {
      this.userNickname = response.data.nickname;
    })
  }

  obtenerLugaresUsuario() {
    this.clientService.obtenerLugaresUsuario(this.userId).then((response) => {
      this.lugaresUsuario = response.data.map((lugar: Lugar) => lugar);
    })
  }

  obtenerFavoritos() {
    this.clientService.obtenerFavoritos(this.userId).then((response) => {
      this.favoritosUsuario = response.data.map((lugar: Lugar) => lugar);
    })
  }

}
