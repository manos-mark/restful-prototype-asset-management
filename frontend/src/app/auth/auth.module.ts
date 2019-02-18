import { NgModule } from '@angular/core';
import { ReactiveFormsModule, NgModel, FormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { AuthRoutingModule } from './auth-routing.module';
import { CommonModule } from '@angular/common';
import { InputTrimModule } from 'ng2-trim-directive';
import { ForgottenPasswordComponent } from './login/forgotten-password/forgotten-password.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    LoginComponent,
    ForgottenPasswordComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    InputTrimModule,
    ReactiveFormsModule,
    SharedModule,
    AuthRoutingModule
  ]
})
export class AuthModule {}