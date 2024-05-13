import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardUserComponent } from './dashboard-user.component';
import { CreateLocalComponent } from '../create-local/create-local.component';
import { CardLocalComponent } from '../card-local/card-local.component';


@NgModule({
    declarations: [
        DashboardUserComponent
    ],
    imports: [
        CommonModule,
        CreateLocalComponent,
        CardLocalComponent
    ],
    exports: [
        DashboardUserComponent
    ]
})
export class DashboardUserModule { }
