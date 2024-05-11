import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { RouterModule } from '@angular/router';
import { RegisterComponent } from '../register/register.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    LoginComponent
  ],
  imports: [
      CommonModule,
      RouterModule,
      RegisterComponent,
      FormsModule,
  ],
  exports: [
    LoginComponent
  ]
})
export class LoginModule { }
