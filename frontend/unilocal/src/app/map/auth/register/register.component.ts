import { Component } from '@angular/core';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  fotoPreview: string | ArrayBuffer | null = 'https://cdn-icons-png.flaticon.com/128/949/949647.png';

  handleFotoSeleccionada(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.fotoPreview = reader.result;
        console.log(this.fotoPreview)
      };
      reader.readAsDataURL(file);
    }
  }
}
