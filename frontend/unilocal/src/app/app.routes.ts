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
import { DashboardModComponent } from './map/components/dashboard-mod/dashboard-mod.component';

export const routes: Routes = [
    { path: 'home', component: HomescreenComponent },
    { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
    { path: 'register', component: RegisterComponent, canActivate: [LoginGuard] },
    { path: 'map', component: MapscreenComponent},
    { path: 'dashboard-user/:id', component: DashboardUserComponent, canActivate: [RolesGuard], data: {
            expectedRole: ["USER"]
        }
    },
    { path: 'dashboard-mod/:id', component: DashboardModComponent, canActivate: [RolesGuard], data: {
            expectedRole: ["MOD"]
        }
    },

    
    { path: '', redirectTo: '/home', pathMatch: 'full' },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export  class AppRoutingModule {}
