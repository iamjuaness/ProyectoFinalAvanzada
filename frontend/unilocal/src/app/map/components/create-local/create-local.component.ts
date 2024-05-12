import { Component, ElementRef, EventEmitter, Output, ViewChild } from '@angular/core';
import { MapService, PlacesService } from '../../services';
import { CreatePlaceDto } from '../../class/dto/create-place-dto';
import { LocalService } from '../../services/local.service';
import { Horario } from '../../class/model/horario';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-create-local',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './create-local.component.html',
  styleUrl: './create-local.component.css'
})
export class CreateLocalComponent {
  @Output() close = new EventEmitter<void>();
  showLocation: boolean = false;
  createPlaceDto: CreatePlaceDto
  horarios: Horario[];
  tipos: string[];
   phones: { telefono: string }[] = [{ telefono: '' }];
  

  closePopup() {
    this.close.emit();
  }

  constructor(private mapaService: MapService, private localService: LocalService, private tokenService: TokenService) {
    this.createPlaceDto = new CreatePlaceDto();
    this.horarios = [new Horario()];
    this.tipos = [];
    this.cargarTipos();
   }

  ngOnInit(): void {
    this.mapaService.crearMapa();
    this.mapaService.agregarMarcador().subscribe((marcador) => {
      this.createPlaceDto.location.lat = marcador.lat;
      this.createPlaceDto.location.lng = marcador.lng;
    });
  }

  public crearNegocio() {
    const idUser = this.tokenService.getCodigo();
    this.createPlaceDto.schedules = this.horarios;
    this.createPlaceDto.phones = this.phones;
    this.createPlaceDto.owner = idUser;
    this.localService.crearLugar(this.createPlaceDto);
    // console.log(this.createPlaceDto)
  }

  public agregarHorario() {
    this.horarios.push(new Horario());
  }

  eliminarHorario(index: number): void {
    this.horarios.splice(index, 1);
  }

  agregarTelefono(): void {
    this.phones.push({ telefono: '' });
  }

  eliminarTelefono(index: number): void {
    this.phones.splice(index, 1);
  }

  public onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const files = event.target.files;
      this.createPlaceDto.images = files;
    }
  }

  private cargarTipos() {
   this.tipos = ["PANADERIA", "CAFETERIA", "BAR", "RESTAURANTE", "DISCOTECA", "SUPERMERCADO", "TIENDA", "OTRO"];
  }
}
