import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardUserComponent } from './dashboard-user.component';


@NgModule({
    declarations: [
        DashboardUserComponent
    ],
    imports: [
        CommonModule
    ],
    exports: [
        DashboardUserComponent
    ]
})
export class DashboardUserModule { }
