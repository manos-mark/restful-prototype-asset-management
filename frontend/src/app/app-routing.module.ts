import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';

const appRoutes: Routes = [
  { path: '', component: LoginComponent }
];

@NgModule({
  imports: [
    // RouterModule.forRoot(appRoutes, {preloadingStrategy: PreloadAllModules})
    RouterModule.forRoot(appRoutes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {

}