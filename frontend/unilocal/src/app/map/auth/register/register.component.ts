import { Component } from '@angular/core';
import { RegistroClienteDTO } from '../../class/dto/registro-cliente-dto';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth-service.service';
import { PublicService } from '../../services/public.service';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  fotoPreview: string | ArrayBuffer | null = 'https://cdn-icons-png.flaticon.com/128/949/949647.png';

  registroClienteDTO: RegistroClienteDTO;
  ciudades: string[];

  constructor(private authService: AuthService, private publicoService: PublicService) {
    this.registroClienteDTO = new RegistroClienteDTO();
    this.ciudades = [];
    this.cargarCiudades();
  }

  handleFotoSeleccionada(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.fotoPreview = reader.result;
      };
      reader.readAsDataURL(file);
      this.registroClienteDTO.photo = file;
    }
  }

  registrar() {
    this.authService.registrarUsuario(this.registroClienteDTO)
  }

  public sonIguales(): boolean {
    return this.registroClienteDTO.password == this.registroClienteDTO.confirmPassword;
  }

  private cargarCiudades() {
    this.publicoService.listarCiudades().subscribe({
      next: (data) => {
        this.ciudades = data.respuesta;
      },
      error: (error) => {
        console.log("Error al cargar las ciudades");
      }
    });
  }
}
