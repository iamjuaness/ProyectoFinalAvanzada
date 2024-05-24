import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth-service.service';
import { ChangePasswordDto } from '../../class/dto/change-password-dto';
import { ToastrService } from 'ngx-toastr';
import { error } from 'console';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [RouterModule, FormsModule],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.css'
})
export class ChangePasswordComponent {
  passwordDto: ChangePasswordDto
  userId: string | null= ''

  constructor(private authService: AuthService,
    private route: ActivatedRoute,
    private toast: ToastrService,
    private router: Router
  ) {
    this.passwordDto = new ChangePasswordDto();
  }

  ngOnInit() {
    this.userId = this.route.snapshot.paramMap.get('id');
    this.passwordDto.id = this.userId;
  }

  cambiarContrasenia() {
    this.authService.cambiarContrasenia(this.passwordDto).then(() => {
      this.toast.success('✅ Contraseña cambiada correctamente', 'UNIOCAL');
      this.router.navigate([`/login`])
    }).catch((error) => {
      this.toast.error('❌ Error al cambiar la contraseña', 'UNILOCAL', error);
    })
  }
}
