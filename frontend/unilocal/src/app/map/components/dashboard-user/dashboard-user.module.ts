import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardUserComponent } from './dashboard-user.component';
import { CreateLocalComponent } from '../create-local/create-local.component';


@NgModule({
    declarations: [
        DashboardUserComponent
    ],
    imports: [
        CommonModule,
        CreateLocalComponent
    ],
    exports: [
        DashboardUserComponent
    ]
})
export class DashboardUserModule { }
