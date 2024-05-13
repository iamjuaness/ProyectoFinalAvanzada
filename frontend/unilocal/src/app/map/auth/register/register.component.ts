import { Component } from '@angular/core';
import { RegistroClienteDTO } from '../../class/dto/registro-cliente-dto';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth-service.service';
import { PublicService } from '../../services/public.service';
import { AlertaComponent } from '../../components/alerta/alerta.component';
import { Alerta } from '../../class/dto/alerta';
import { Ciudad } from '../../class/model/ciudad';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, AlertaComponent],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  fotoPreview: string | ArrayBuffer | null = 'https://cdn-icons-png.flaticon.com/128/949/949647.png';
  alerta!:Alerta;

  registroClienteDTO: RegistroClienteDTO;
  ciudades: Ciudad[];

  constructor(private authService: AuthService, private publicoService: PublicService) {
    this.registroClienteDTO = new RegistroClienteDTO();
    this.ciudades = [new Ciudad()];
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

  public registrar() {
    if (this.registroClienteDTO.photo != "") {
      this.authService.registrarUsuario(this.registroClienteDTO)
        .then((data) => {
          // Manejar el éxito del registro
          this.alerta = new Alerta(data, "success");
        })
        .catch((error) => {
          // Manejar el error durante el registro
          this.alerta = new Alerta(error.response.data.message, "danger");
        });
    } else {
      this.alerta = new Alerta("Debe subir una imagen", "danger");
    }
  }

  public sonIguales(): boolean {
    return this.registroClienteDTO.password == this.registroClienteDTO.confirmPassword;
  }

  private cargarCiudades() {
    this.publicoService.listarCiudades().then((response) => {
      // Iterar sobre cada objeto Ciudad en response.data
      this.ciudades = response.data.map((ciudad: Ciudad) => ciudad.nombre);
    })
    .catch((error) => {
      console.log("Error al obtener las ciudades")
    })
  }
}
