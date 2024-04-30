import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { RouterModule } from '@angular/router';
import { RegisterComponent } from '../register/register.component';

@NgModule({
  declarations: [
    LoginComponent
  ],
  imports: [
      CommonModule,
      RouterModule,
      RegisterComponent
  ],
  exports: [
    LoginComponent
  ]
})
export class LoginModule { }
