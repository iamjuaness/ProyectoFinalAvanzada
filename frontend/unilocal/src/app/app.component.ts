import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { HeaderComponent } from './map/shared/header/header.component';
import { DashboardComponent } from './map/pages/dashboard/dashboard.component';
import { FooterComponent } from './map/shared/footer/footer.component';
import { MapsModule } from './map/maps.module';
import { RegisterComponent } from './map/auth/register/register.component';
import { ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HeaderComponent,
    FooterComponent,
    RouterOutlet,
    DashboardComponent,
    RegisterComponent,
    ReactiveFormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'unilocal';
}
