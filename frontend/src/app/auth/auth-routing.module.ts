import { Routes, RouterModule, Router } from "@angular/router";
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';

const authRoutes: Routes = [
  { path: '', component: LoginComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(authRoutes)
  ],
  exports: [RouterModule]
})
export class AuthRoutingModule {}