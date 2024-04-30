import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header.component';
import { NavModule } from '../nav/nav.module';

@NgModule({
  declarations: [
    HeaderComponent
  ],
  imports: [
    CommonModule,
    NavModule
  ],
  exports: [
    HeaderComponent
  ]
})
export class HeaderModule { }
