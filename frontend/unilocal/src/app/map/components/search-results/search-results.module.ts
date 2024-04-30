import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchResultsComponent } from './search-results.component';
import { PlacePopupModule } from '../place-popup/place-popup.module';


@NgModule({
  declarations: [
    SearchResultsComponent
  ],
  imports: [
    CommonModule,
    PlacePopupModule
  ],
  exports: [
    SearchResultsComponent
  ]
})
export class SearchResultsModule { }
