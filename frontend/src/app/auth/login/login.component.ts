import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Subscription } from 'rxjs';
import { User } from '../user.model';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit, OnDestroy {
  subscription: Subscription;
  currentUser: User;
  rememberMe = false;
  forgottenPass = false;

  loginForm = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.email]),
    password: new FormControl(null, [Validators.required, Validators.minLength(6)])
  })

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.authService.getCurrentUser().subscribe(
      resp => { this.currentUser = resp.body; console.log(this.currentUser) },
      error => { if (error.status !== 403) console.log(error) }
    );
  }


  onSubmit() {
    this.subscription = this.authService.loginUser(
                            this.loginForm.value.email, 
                            this.loginForm.value.password,
                            this.rememberMe)
      .subscribe(
        res => console.log(res),
        error => console.log(error)
    );
  }

  onForgottenPass() {
    this.forgottenPass = !this.forgottenPass;
  }

  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
