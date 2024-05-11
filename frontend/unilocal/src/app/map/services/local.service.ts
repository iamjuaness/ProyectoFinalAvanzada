import { Injectable } from '@angular/core';
import { CreatePlaceDto } from '../class/dto/create-place-dto';
import { ImagenesService } from './imagenes.service';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class LocalService {

  public baseUrl: string = 'http://localhost:8080/api/place';

  constructor(private imagenesService: ImagenesService) { }

  public async crearLugar(createPlaceDto: CreatePlaceDto) {
    // Convertir FileList a un arreglo de archivos
    const imagesToUpload: File[] = Array.from(createPlaceDto.images);
    const images: string[] = [];
        // Subir cada imagen a Cloudinary
    const uploadPromises = imagesToUpload.map(photo => this.imagenesService.subirImagen(photo).then(response => (
      images.push(response.data.respuesta.secure_url)
    )));

    // Esperar a que todas las imágenes se suban
    await Promise.all(uploadPromises);

    createPlaceDto.images = images;

    // Luego realizar la petición para guardar el lugar
    const response = await axios.post(`${this.baseUrl}/create-place`, createPlaceDto);
    return response.data;
  }
}
