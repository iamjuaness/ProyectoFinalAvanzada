import { CommonModule } from '@angular/common';
import { Component, OnInit  } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SearchBarModule } from './map/components/search-bar/search-bar.module';
import { SearchResultsModule } from './map/components/search-results/search-results.module';
import { BtnMyLocationModule } from './map/components/btn-my-location/btn-my-location.module';
import { MapsModule } from './map/maps.module';
import { HeaderModule } from './map/shared/header/header.module';
import { FooterModule } from './map/shared/footer/footer.module';
import { DashboardModule } from './map/pages/dashboard/dashboard.module';
import { LoginModule } from './map/auth/login/login.module';
import { HomeScreenModule } from './map/screens/homescreen/homescreen.module';
import { NavModule } from './map/shared/nav/nav.module';
import { TokenService } from './map/services/token.service';
import { DashboardUserModule } from './map/components/dashboard-user/dashboard-user.module';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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
    NavModule,
    DashboardUserModule,
    MatSnackBarModule,
  ],
  styleUrl: './app.component.css',
  standalone: true
})
export class AppComponent implements OnInit {
  title = 'unilocal';

    userLoginOn: boolean = false;

  constructor(private tokenService: TokenService) { }

  ngOnInit(): void {
    this.userLoginOn = this.tokenService.isLogged();
  }
}
