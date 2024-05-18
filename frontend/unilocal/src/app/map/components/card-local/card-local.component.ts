import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Lugar } from '../../class/model/lugar';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-card-local',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card-local.component.html',
  styleUrl: './card-local.component.css'
})
export class CardLocalComponent implements OnInit, OnDestroy {
  @Input() lugar!: Lugar;
  currentImageIndex: number = 0;
  intervalId: any;

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
}
