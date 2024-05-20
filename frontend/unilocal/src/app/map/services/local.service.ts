import { Injectable } from '@angular/core';
import { CreatePlaceDto } from '../class/dto/create-place-dto';
import { ImagenesService } from './imagenes.service';
import axios, { AxiosRequestConfig } from 'axios';
import { usuarioInterceptor } from '../interceptor/usuario.interceptor';
import { TokenService } from './token.service';
import { environment } from '../../environments/environment';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { error } from 'console';
import { Lugar } from '../class/model/lugar';

@Injectable({
  providedIn: 'root'
})
export class LocalService {


  constructor(private imagenesService: ImagenesService,
    private tokenService: TokenService,
    private router: Router,
  private toastrService: ToastrService) { }

  public async crearLugar(createPlaceDto: CreatePlaceDto) {
    // Convertir FileList a un arreglo de archivos
    const imagesToUpload: File[] = Array.from(createPlaceDto.images);
    const images: string[] = [];
    const id: string = createPlaceDto.owner;
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
    await axios.post(`${environment.urlPlace}/create-place`, createPlaceDto, { headers }).then((response) => {
      this.toastrService.success('✅ Lugar creado con éxito', 'UNILOCAL');
      return response.data;
    }
    ).catch((error) => {
      this.toastrService.error('❌ Error al crear el lugar', 'UNILOCAL');
      return error.error;
    });
    this.router.navigate([`/dashboard-user/${{id}}`]).then(() => {
      window.location.reload();
    });
  }


  obtenerLugar(idLugar: number) {
    // Configurar el token JWT en el encabezado Authorization
    const token = this.tokenService.getToken(); // Función para obtener el token JWT
    const headers: AxiosRequestConfig['headers'] = {
      Authorization: `Bearer ${token}`
    };
    return axios.get<Lugar>(`${environment.urlPlace}/get-place/${idLugar}`, {headers});
  }
}
