import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './nav.component';
import { RouterLink, RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    NavComponent,
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    NavComponent, // Asegúrate de exportar NavComponent aquí
  ]
})
export class NavModule { }
