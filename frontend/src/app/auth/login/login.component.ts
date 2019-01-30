import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Subscription } from 'rxjs';
import { User } from '../user.model';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  

  loginForm = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.email]),
    password: new FormControl(null, [Validators.required, Validators.minLength(8)])
  })

  constructor(public authService: AuthService,
              private router: Router) { }

  ngOnInit() {
    this.authService.getCsrf();   
  }


  onSubmit() {
    this.authService.loginUser(this.loginForm.value.email, 
      this.loginForm.value.password);
  }

  onForgottenPass() {
    this.authService.forgottenPass = !this.authService.forgottenPass;
  }

  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }
}
