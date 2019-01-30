import { NgModule } from "@angular/core";
import { ProductsComponent } from './products/products.component';
import { ProjectsComponent } from './projects/projects.component';
import { CommonModule } from '@angular/common';
import { PrototypeRoutingModule } from './prototype-routing.module';
import { EditProjectComponent } from './projects/edit-project/edit-project.component';
import { ListProjectsComponent } from './projects/list-projects/list-projects.component';
import { ListProductsComponent } from './products/list-products/list-products.component';
import { EditProductComponent } from './products/edit-product/edit-product.component';

@NgModule({
    declarations: [
        ProductsComponent,
        ProjectsComponent,
        EditProjectComponent,
        ListProjectsComponent,
        ListProductsComponent,
        EditProductComponent
    ],
    imports: [
        CommonModule,
        PrototypeRoutingModule
    ]
})
export class PrototypeModule {}