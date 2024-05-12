import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';
import { DashboardComponent } from './map/pages/dashboard/dashboard.component';
import { LoginComponent } from './map/auth/login/login.component';
import { RegisterComponent } from './map/auth/register/register.component';
import { MapscreenComponent } from './map/screens/mapscreen/mapscreen.component';
import { HomescreenComponent } from './map/screens/homescreen/homescreen.component';
import { DashboardUserComponent } from './map/components/dashboard-user/dashboard-user.component';
import { LoginGuard } from './map/services/permiso.service';
import { RolesGuard } from './map/services/roles.service';

export const routes: Routes = [
    { path: 'home', component: HomescreenComponent },
    { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
    { path: 'register', component: RegisterComponent, canActivate: [LoginGuard] },
    { path: 'map', component: MapscreenComponent},
    { path: 'dashboard-user/:id', component: DashboardUserComponent, canActivate: [RolesGuard], data: {
            expectedRole: ["USER"]
        }
    },

    
    { path: '', redirectTo: '/home', pathMatch: 'full' },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export  class AppRoutingModule {}
