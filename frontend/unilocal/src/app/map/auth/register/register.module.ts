import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { RegisterComponent } from '../register/register.component';
import { FormsModule } from '@angular/forms';
import { AlertaComponent } from '../../components/alerta/alerta.component';

@NgModule({
  declarations: [
    // RegisterComponent
  ],
  imports: [
      CommonModule,
      RouterModule,
      FormsModule, 
      AlertaComponent
  ],
  exports: [
    // RegisterComponent
  ]
})
export class RegisterModule { }
