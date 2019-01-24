import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { AuthRoutingModule } from './auth-routing.module';
import { CommonModule } from '@angular/common';
import { InputTrimModule } from 'ng2-trim-directive';
import { HttpClientXsrfModule, HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    LoginComponent
  ],
  imports: [
    CommonModule,
    InputTrimModule,
    // HttpClientModule,
    // HttpClientXsrfModule,
    ReactiveFormsModule,
    AuthRoutingModule
  ]
})
export class AuthModule {}