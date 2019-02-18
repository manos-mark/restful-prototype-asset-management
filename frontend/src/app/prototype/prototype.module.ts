import { NgModule } from "@angular/core";
import { ProductsComponent } from './products/products.component';
import { ProjectsComponent } from './projects/projects.component';
import { CommonModule } from '@angular/common';
import { PrototypeRoutingModule } from './prototype-routing.module';
import { EditProjectComponent } from './projects/edit-project/edit-project.component';
import { ListProjectsComponent } from './projects/list-projects/list-projects.component';
import { ListProductsComponent } from './products/list-products/list-products.component';
import { EditProductComponent } from './products/edit-product/edit-product.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ProductItemComponent } from './products/list-products/product-item/product-item.component';
import { ProjectItemComponent } from './projects/list-projects/project-item/project-item.component';

@NgModule({
    declarations: [
        ProductsComponent,
        ProjectsComponent,
        EditProjectComponent,
        EditProductComponent,
        ListProjectsComponent,
        ListProductsComponent,
        ProductItemComponent,
        ProjectItemComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        PrototypeRoutingModule
    ]
})
export class PrototypeModule {}