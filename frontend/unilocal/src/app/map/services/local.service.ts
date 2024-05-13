import { Injectable } from '@angular/core';
import { CreatePlaceDto } from '../class/dto/create-place-dto';
import { ImagenesService } from './imagenes.service';
import axios, { AxiosRequestConfig } from 'axios';
import { usuarioInterceptor } from '../interceptor/usuario.interceptor';
import { TokenService } from './token.service';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LocalService {


  constructor(private imagenesService: ImagenesService, private tokenService: TokenService) { }

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

    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
        Authorization: `Bearer ${token}`
    };

    // Luego realizar la petición para guardar el lugar
    const response = await axios.post(`${environment.urlPlace}/create-place`, createPlaceDto, {headers});
    return response.data;
  }
}
