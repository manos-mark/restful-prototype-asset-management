import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Subscription } from 'rxjs';
import { User } from '../user.model';
import { Router } from '@angular/router';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { WindowPopService } from 'src/app/shared/window-pop/window-pop.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
    windowPopFail = false;
    windowPop = false;
    forgottenPass = false;

  loginForm = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.email]),
    password: new FormControl(null, [Validators.required, Validators.minLength(6)])
  })

  constructor(public authService: AuthService,
              private router: Router,
              private activityService: ActivityService,
              private windowPopService: WindowPopService) { }

  ngOnInit() {
    this.authService.getCsrf();   
  }


  onSubmit() {
    this.authService.loginUser(this.loginForm.value.email, this.loginForm.value.password)
      .subscribe(
        res => { 
          this.router.navigate(['/']);
        },
        error => {
          if (error.status === 200) {
            this.authService.getCurrentUser();
            this.activityService.addActivity('1').subscribe();
            this.router.navigate(['/']);
          } else {
            this.windowPopService.title = "Authentication Failed";
            this.windowPopService.context = "Your request is not successful!";
            this.windowPopService.details = "Try again with different credentials.";
            this.windowPopService.activate = true;
          }
        }
    );
  }

  onForgottenPass() {
    this.forgottenPass = !this.forgottenPass;
  }

  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }
}
