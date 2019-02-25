import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from '@angular/forms';
import { EditProductComponent } from './products/edit-product/edit-product.component';
import { ListProductsComponent } from './products/list-products/list-products.component';
import { ProductItemComponent } from './products/list-products/product-item/product-item.component';
import { ProductsComponent } from './products/products.component';
import { EditProjectComponent } from './projects/edit-project/edit-project.component';
import { ListProjectsComponent } from './projects/list-projects/list-projects.component';
import { ProjectsComponent } from './projects/projects.component';
import { PrototypeRoutingModule } from './prototype-routing.module';
import { ProductPictureComponent } from './products/edit-product/product-picture/product-picture.component';

@NgModule({
    declarations: [
        ProductsComponent,
        ProjectsComponent,
        EditProjectComponent,
        EditProductComponent,
        ListProjectsComponent,
        ListProductsComponent,
        ProductItemComponent,
        ProductPictureComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        PrototypeRoutingModule
    ]
})
export class PrototypeModule {}