import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { SearchBarModule } from './map/components/search-bar/search-bar.module';
import { SearchResultsModule } from './map/components/search-results/search-results.module';
import { BtnMyLocationModule } from './map/components/btn-my-location/btn-my-location.module';
import { MapsModule } from './map/maps.module';
import { HeaderModule } from './map/shared/header/header.module';
import { FooterModule } from './map/shared/footer/footer.module';
import { DashboardModule } from './map/pages/dashboard/dashboard.module';
import { routes } from './app.routes';
import { RegisterComponent } from './map/auth/register/register.component';
import { LoginModule } from './map/auth/login/login.module';
import { HomeScreenModule } from './map/screens/homescreen/homescreen.module';
import { NavModule } from './map/shared/nav/nav.module';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  imports: [
    CommonModule,
    SearchBarModule,
    SearchResultsModule,
    BtnMyLocationModule,
    MapsModule,
    HeaderModule,
    FooterModule,
    DashboardModule,
    RouterModule,
    LoginModule,
    HomeScreenModule,
    NavModule
  ],
  styleUrl: './app.component.css',
  standalone: true // Agrega esta línea
})
export class AppComponent {
  title = 'unilocal';
}
