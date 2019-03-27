import { NgModule } from "@angular/core";
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from '../auth/auth-guard.service';
import { EditProjectComponent } from './projects/edit-project/edit-project.component';
import { ListProjectsComponent } from './projects/list-projects/list-projects.component';
import { EditProductComponent } from './products/edit-product/edit-product.component';
import { ListProductsComponent } from './products/list-products/list-products.component';

const prototypeRoutes: Routes = [
    { path: 'prototype', canActivateChild: [AuthGuard], children: [
        { path: 'projects', component: ListProjectsComponent},
        { path: 'projects/:id/edit', component: EditProjectComponent},
        { path: 'projects/new', component: EditProjectComponent},
        { path: 'products', component: ListProductsComponent},
        { path: 'products/new', component: EditProductComponent},
        { path: 'products/:id/edit', component: EditProductComponent}
    ] },
    { path: '**', redirectTo: 'general', pathMatch: 'full' }
];

@NgModule({
    imports: [
        RouterModule.forChild(prototypeRoutes)
    ],
    exports: [RouterModule]
})
export class PrototypeRoutingModule {}