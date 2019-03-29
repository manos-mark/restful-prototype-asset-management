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
import { WindowPopService } from './shared/window-pop/window-pop.service';
import { ImageCarouselService } from './shared/image-carousel/image-carousel.service';
import { LightboxModule } from 'ngx-lightbox';
import { BreadcrumbsService } from './shared/breadcrumbs.service';
import { NotificationService } from './shared/notification/notification.service';
import { SearchService } from './header/search/search.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoaderService } from './shared/loader/loader.service';


@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    FormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    HttpClientXsrfModule,
    SharedModule,
    HeaderModule,
    GeneralModule,
    ProfileModule,
    AppRoutingModule
  ],
  providers: [
    AuthService,
    ActivityService,
    BreadcrumbsService,
    AuthGuard,
    TitleCasePipe,
    ProjectsService,
    ProductsService,
    WindowPopService,
    ImageCarouselService,
    NotificationService,
    LoaderService,
    SearchService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
