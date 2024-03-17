import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MapscreenComponent } from './screens/mapscreen/mapscreen.component';
import { LoadingComponent } from './components/loading/loading.component';
import { MapViewComponent } from './components/map-view/map-view.component';



@NgModule({
  declarations: [
    MapscreenComponent,
    LoadingComponent,
    MapViewComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    MapscreenComponent,
    LoadingComponent
  ]
})
export class MapsModule { }
