import { NgModule } from '@angular/core';
import { HeaderComponent } from './header.component';
import { SearchComponent } from './search/search.component';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from '../app-routing.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
    declarations: [
        HeaderComponent,
        SearchComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        AppRoutingModule
    ],
    exports: [
        HeaderComponent,
        HeaderComponent,
        AppRoutingModule
    ]
})
export class HeaderModule {}
