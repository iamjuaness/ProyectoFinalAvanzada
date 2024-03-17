import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser'; 

import { AppComponent } from './app.component';

import { MapsModule } from './maps/maps.module';
import { PlacesService } from './maps/services';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
    declarations: [
      AppComponent
    ],
    imports: [
      BrowserModule,
      MapsModule,
      HttpClientModule,
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
