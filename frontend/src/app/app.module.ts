import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AuthModule } from './auth/auth.module';
import { AuthService } from './auth/auth.service';
import { AuthGuard } from './auth/auth-guard.service';
import { HeaderModule } from './header/header.module';
import { ProfileModule } from './profile/profile.module';
import { GeneralModule } from './general/general.module';
import { TitleCasePipe } from '@angular/common';
import { PrototypeModule } from './prototype/prototype.module';
import { ActivityService } from './general/home/activity/activity.service';
import { ProjectsService } from './prototype/projects/projects.service';
import { ProductsService } from './prototype/products/products.service';
import { FormsModule } from '@angular/forms';
import { SharedModule } from './shared/shared.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    HttpClientModule,
    HttpClientXsrfModule,
    AuthModule,
    HeaderModule,
    GeneralModule,
    PrototypeModule,
    ProfileModule,
    SharedModule,
    AppRoutingModule
  ],
  providers: [
    AuthService, 
    ActivityService, 
    AuthGuard, 
    TitleCasePipe, 
    ProjectsService,
    ProductsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
