import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { Lugar } from '../../class/model/lugar';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../../services/cliente.service';
import { error } from 'console';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-card-local',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card-local.component.html',
  styleUrl: './card-local.component.css'
})
export class CardLocalComponent implements OnInit, OnDestroy {
  @Input() lugar!: Lugar;
  @Input() usuarioId!: string; // Obtén este valor de tu contexto de usuario
  @Input() showFavoritos!: boolean;
  @Output() favoritoAgregado = new EventEmitter<void>();
  @Input() favoritosUsuario!: Lugar[]; // Lista de favoritos del usuario
  currentImageIndex: number = 0;
  intervalId: any;

  constructor(private clientService: ClienteService, private toastrService: ToastrService){}

  ngOnInit(): void {
    this.startImageCarousel();
  }

  ngOnDestroy(): void {
    clearInterval(this.intervalId);
  }

  startImageCarousel(): void {
    this.intervalId = setInterval(() => {
      this.currentImageIndex = (this.currentImageIndex + 1) % this.lugar.images.length;
    }, 4500); // Cambia la imagen cada 4.5 segundos
  }

  onAddToFavorites(lugarId: number) {
    const index = this.favoritosUsuario.findIndex(l => l.id === lugarId);
    if (index !== -1) {
      // Si el lugar ya está en favoritos, lo eliminamos
      this.clientService.eliminarFavoritos(this.usuarioId, lugarId).then((response) => {
        console.log('Eliminado de favoritos');
        this.favoritoAgregado.emit(); // Actualizamos la lista de favoritos
        this.toastrService.info('❌ Eliminado de mis favoritos', 'UNILOCAL')
      }).catch((error) => {
        console.error('Error al eliminar de favoritos', error);
        this.toastrService.error(`Error al eliminar de favoritos ${error}`, 'UNILOCAL')
      });
    } else {
      // Si el lugar no está en favoritos, lo agregamos
      this.clientService.agregarFavorito(this.usuarioId, lugarId).then((response) => {
        console.log('Agregado a favoritos');
        this.favoritoAgregado.emit(); // Actualizamos la lista de favoritos
        this.toastrService.success('❤️ Agregado a mis favoritos', 'UNILOCAL')
      }).catch((error) => {
        console.error('Error al agregar a favoritos', error);
        this.toastrService.error(`Error al agregar a favoritos ${error}`, 'UNILOCAL')
      });
    }
  }

  isFavorite(lugarId: number): boolean {
    return this.favoritosUsuario.some(l => l.id === lugarId);
  }
}
