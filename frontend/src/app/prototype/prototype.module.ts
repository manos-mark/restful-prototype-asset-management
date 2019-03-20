import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { EditProductComponent } from './products/edit-product/edit-product.component';
import { ListProductsComponent } from './products/list-products/list-products.component';
import { ProductsComponent } from './products/products.component';
import { EditProjectComponent } from './projects/edit-project/edit-project.component';
import { ListProjectsComponent } from './projects/list-projects/list-projects.component';
import { ProjectsComponent } from './projects/projects.component';
import { PrototypeRoutingModule } from './prototype-routing.module';
import { OwlDateTimeModule, OwlNativeDateTimeModule, OWL_DATE_TIME_LOCALE, OWL_DATE_TIME_FORMATS } from 'ng-pick-datetime';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

export const MY_NATIVE_FORMATS = {
    datePickerInput: {year: 'numeric', month: 'numeric', day: 'numeric'}
};

@NgModule({
    declarations: [
        ProductsComponent,
        ProjectsComponent,
        EditProjectComponent,
        EditProductComponent,
        ListProjectsComponent,
        ListProductsComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        SharedModule,
        ReactiveFormsModule,
        PrototypeRoutingModule,
        OwlDateTimeModule,
        OwlNativeDateTimeModule,
        BrowserAnimationsModule,
    ],
    providers: [
        {provide: OWL_DATE_TIME_LOCALE, useValue: 'en-GB'}
    ]
})
export class PrototypeModule {}