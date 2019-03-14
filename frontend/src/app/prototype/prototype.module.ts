import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from '@angular/forms';
import { EditProductComponent } from './products/edit-product/edit-product.component';
import { ListProductsComponent } from './products/list-products/list-products.component';
import { ProductsComponent } from './products/products.component';
import { EditProjectComponent } from './projects/edit-project/edit-project.component';
import { ListProjectsComponent } from './projects/list-projects/list-projects.component';
import { ProjectsComponent } from './projects/projects.component';
import { PrototypeRoutingModule } from './prototype-routing.module';
import { SharedModule } from '../shared/shared.module';

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
        SharedModule,
        ReactiveFormsModule,
        PrototypeRoutingModule
    ]
})
export class PrototypeModule {}