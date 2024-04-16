import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser'; 
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';

import { AppComponent } from './app.component';

import { MapsModule } from './maps/maps.module';
import { PlacesService } from './maps/services';
import { HttpClientModule } from '@angular/common/http';
import {CloudinaryModule} from '@cloudinary/ng';
import { AppRoutingModule } from './app-routing.module';

@NgModule({
    declarations: [
      AppComponent
    ],
    imports: [
      BrowserModule,
      MapsModule,
      HttpClientModule,
      CloudinaryModule,
      AppRoutingModule,
      MatDialogModule,
      MatFormFieldModule,
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
