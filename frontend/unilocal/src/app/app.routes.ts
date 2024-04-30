import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';
import { DashboardComponent } from './map/pages/dashboard/dashboard.component';
import { LoginComponent } from './map/auth/login/login.component';
import { RegisterComponent } from './map/auth/register/register.component';
import { MapscreenComponent } from './map/screens/mapscreen/mapscreen.component';
import { HomescreenComponent } from './map/screens/homescreen/homescreen.component';

export const routes: Routes = [
    { path: 'home', component: HomescreenComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'map', component: MapscreenComponent},
    { path: '', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export  class AppRoutingModule {}
