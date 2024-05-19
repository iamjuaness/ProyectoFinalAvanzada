import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardModComponent } from './dashboard-mod.component';


@NgModule({
    declarations: [
        DashboardModComponent
    ],
    imports: [
        CommonModule
    ],
    exports: [ DashboardModComponent ]
})
export class DashboardModModule { }
