import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { AuthRoutingModule } from './auth-routing.module';
import { CommonModule } from '@angular/common';
import { InputTrimModule } from 'ng2-trim-directive';
import { HttpClientXsrfModule, HttpClientModule } from '@angular/common/http';
import { ForgottenPasswordComponent } from './login/forgotten-password/forgotten-password.component';


@NgModule({
  declarations: [
    LoginComponent,
    ForgottenPasswordComponent
  ],
  imports: [
    CommonModule,
    InputTrimModule,
    ReactiveFormsModule,
    AuthRoutingModule
  ]
})
export class AuthModule {}