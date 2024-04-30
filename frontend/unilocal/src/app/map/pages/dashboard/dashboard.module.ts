import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { MapsModule } from '../../maps.module';

@NgModule({
  declarations: [
    DashboardComponent
  ],
  imports: [
    CommonModule,
    MapsModule
  ],
  exports: [
    DashboardComponent
  ]
})
export class DashboardModule { }
