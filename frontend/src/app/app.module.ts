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
import { WindowPopComponent } from './window-pop/window-pop.component';
import { GeneralModule } from './general/general.module';
import { TitleCasePipe } from '@angular/common';
import { PrototypeModule } from './prototype/prototype.module';

@NgModule({
  declarations: [
    WindowPopComponent,
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpClientXsrfModule,
    AuthModule,
    HeaderModule,
    GeneralModule,
    PrototypeModule,
    ProfileModule,
    AppRoutingModule
  ],
  providers: [AuthService, AuthGuard, TitleCasePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
