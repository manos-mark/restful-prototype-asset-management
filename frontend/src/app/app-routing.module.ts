import { NgModule } from '@angular/core';
import { Routes, RouterModule, PreloadAllModules } from '@angular/router';
import { GeneralComponent } from './general/general.component';
import { AuthGuard } from './auth/auth-guard.service';
import { ProfileComponent } from './profile/profile.component';
import { HomeComponent } from './general/home/home.component';

const appRoutes: Routes = [
  { path: '', redirectTo: 'general', pathMatch: 'full' },
  { path: 'login', loadChildren: './auth/auth.module#AuthModule' },
  { path: 'prototype', loadChildren: './prototype/prototype.module#PrototypeModule', canActivate: [AuthGuard] },
  { path: 'general', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'general', pathMatch: 'full' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes, {preloadingStrategy: PreloadAllModules})
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {

}