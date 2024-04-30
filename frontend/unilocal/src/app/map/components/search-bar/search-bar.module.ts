import { NgModule } from '@angular/core';
import { SearchResultsComponent } from '../search-results/search-results.component';
import { SearchResultsModule } from '../search-results/search-results.module';
import { SearchBarComponent } from './search-bar.component';



@NgModule({
    declarations: [
      SearchBarComponent
    ],
    imports: [
        SearchResultsModule
    ],
    exports: [
        SearchBarComponent
    ]
})
export class SearchBarModule { }
