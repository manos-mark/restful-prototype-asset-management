import { Component, OnInit, OnDestroy } from '@angular/core';
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

  loginForm = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.email]),
    password: new FormControl(null, [Validators.required, Validators.minLength(6)])
  })

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.authService.getCurrentUser().subscribe(
      resp => this.currentUser = resp.body,
      error => console.log(error)
    );
  }


  onSubmit() {
    this.subscription = this.authService.loginUser(this.loginForm.value.email, this.loginForm.value.password)
      .subscribe(
        res => console.log(res.status, res.response),
        error => console.log(error)
      );
    console.log(this.currentUser)
  }

  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
