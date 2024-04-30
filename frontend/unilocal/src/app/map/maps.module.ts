import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapscreenComponent } from './screens/mapscreen/mapscreen.component';
import { MapsViewModule } from './components/map-view/map-view.module';
import { LoadingModule } from './components/loading/loading.module';
import { BtnMyLocationModule } from './components/btn-my-location/btn-my-location.module';
import { SearchBarModule } from './components/search-bar/search-bar.module';
import { NavModule } from './shared/nav/nav.module';
import { RouterModule } from '@angular/router';


@NgModule({
    declarations: [
        MapscreenComponent
    ],
    imports: [
        MapsViewModule,
        LoadingModule, // Agrega LoadingModule a los imports
        BtnMyLocationModule,
        SearchBarModule,
        NavModule,
        RouterModule,
        CommonModule
    ],
    exports: [
        MapscreenComponent
    ]
})
export class MapsModule { }
