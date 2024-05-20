import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../services/cliente.service';
import { LocalService } from '../../services/local.service';
import { ActivatedRoute } from '@angular/router';
import { Lugar } from '../../class/model/lugar';
import { Person } from '../../class/model/person';
import { response } from 'express';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  imports: [CommonModule],
  selector: 'app-detail-local',
  templateUrl: './detail-local.component.html',
  styleUrls: ['./detail-local.component.css']
})
export class DetailLocalComponent implements OnInit {
  local: Lugar = { images: [] } as unknown as Lugar;
  cliente: Person = {} as Person;
  lugarId: number = 0;
  currentImageIndex: number = 0;
  intervalId: any;

  constructor(
    private route: ActivatedRoute,
    private localService: LocalService,
    private clienteService: ClienteService,
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.lugarId = idParam ? parseInt(idParam, 10) : 0;
    this.obtenerLugar();
    this.startImageCarousel();
  }

  obtenerLugar(): void {
    if (this.lugarId !== 0) {
      this.localService.obtenerLugar(this.lugarId).then((response) => {
        this.local = response.data;
        this.obtenerDueño(this.local.owner);
      }).catch((error) => {
        console.log('Error al obtener el lugar', error)
      })
    }
  }

  obtenerDueño(ownerId: string): void {
    this.clienteService.getPerson(ownerId).then((response) => {
      this.cliente = response.data;
    }).catch((error) => {
      console.log('Error al obtener el dueño', error);
    })
  }

  ngOnDestroy(): void {
    clearInterval(this.intervalId);
  }

  startImageCarousel(): void {
    this.intervalId = setInterval(() => {
      this.currentImageIndex = (this.currentImageIndex + 1) % this.local.images.length;
    }, 4500); // Cambia la imagen cada 4.5 segundos
  }
}
