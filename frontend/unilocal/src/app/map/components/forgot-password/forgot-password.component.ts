import { Component, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { EmailDto } from '../../class/dto/email-dto';
import { AuthService } from '../../services/auth-service.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [RouterModule, FormsModule],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordComponent {

  emailDto: EmailDto
  idUser: string =''
  

  constructor(private authService: AuthService,
    private toastr: ToastrService
  ) {
    this.emailDto = new EmailDto();
  }

  ngOnInit() {
  }

  async obtenerUsuario(email: string) {
    await this.authService.obtenerUsuarios().then((response) => {
      // Buscar el usuario por correo
      const usuario = response.data.find((u: { email: string; }) => u.email === email);
      this.idUser = usuario.cedula
    }).catch((error) => {
      this.toastr.error('Error al obtener los usuarios', 'Error');
    })
  }

  async enviarLink() {
    await this.obtenerUsuario(this.emailDto.destinatario)
    this.emailDto.asunto = 'Recuperación de contraseña'
    this.emailDto.body = `Link para cambiar la contraseña ==> http://localhost:4200/change-password/${this.idUser}`
    await this.authService.recuperarContrasenia(this.emailDto).then(() => {
      this.toastr.success('✅ Correo enviado correctamente, revisa tu correo electronico', 'UNILOCAL')
    }).catch((error) => {
      this.toastr.error('❌ Error al enviar el correo', 'UNILOCAL', error)
    })
  }

}
