import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HeaderModule } from '../../shared/header/header.module';
import { FooterModule } from '../../shared/footer/footer.module';
import { HomescreenComponent } from './homescreen.component';
import { NavModule } from '../../shared/nav/nav.module';
import { DashboardModule } from '../../pages/dashboard/dashboard.module';


@NgModule({
    declarations: [
        HomescreenComponent
    ],
    imports: [
        HeaderModule,
        NavModule,
        DashboardModule,
        FooterModule
    ],
    exports: [
        HomescreenComponent
    ]
})
export class HomeScreenModule { }
