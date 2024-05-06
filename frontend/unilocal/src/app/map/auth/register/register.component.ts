import { Component } from '@angular/core';
import { RegistroClienteDTO } from '../../class/dto/registro-cliente-dto';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth-service.service';


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

  constructor(private authService: AuthService ) {
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
      this.registroClienteDTO.photo = file.name;
    }
  }

  registrar() {
    this.authService.registrarUsuario(this.registroClienteDTO)
  }

  public sonIguales(): boolean {
    return this.registroClienteDTO.password == this.registroClienteDTO.confirmPassword;
  }

  private cargarCiudades() {
   this.ciudades = ["Bogotá", "Medellín", "Cali", "Barranquilla", "Cartagena", "Armenia"];
  }
}
