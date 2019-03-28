import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Subscription } from 'rxjs';
import { User } from '../user.model';
import { Router } from '@angular/router';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { WindowPopService } from 'src/app/shared/window-pop/window-pop.service';
import { Actions } from 'src/app/general/home/activity/action.enum';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
    EMAIL_PATTERN = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    windowPopFail = false;
    windowPop = false;
    forgottenPass = false;

  loginForm = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.pattern(this.EMAIL_PATTERN)]),
    password: new FormControl(null, [Validators.required, Validators.minLength(6)])
  });

  constructor(public authService: AuthService,
              private router: Router,
              private activityService: ActivityService,
              private windowPopService: WindowPopService) { }

  ngOnInit() {
    // to get the csrf cookie
    this.authService.retrieveCurrentUser()
        .subscribe(
            resp => {
                this.authService.setCurrentUser(resp.body);
                // if (this.authService.getCurrentUser()) {
                //     this.router.navigate(['/']);
                // }
            },
            error => { console.log(error); }
        );
  }


  onSubmit() {
    this.authService.loginUser(this.loginForm.value.email, this.loginForm.value.password)
      .subscribe(
        res => {
            if (res.body) {
                this.authService.setCurrentUser(res.body);
                this.router.navigate(['/']);
            } else {
                this.windowPopService.setTitle('Authentication Failed');
                this.windowPopService.setContext('Your request is not successful!');
                this.windowPopService.setDetails('Try again with different credentials.');
                this.windowPopService.activate();
            }
        },
        error => {
            if (error.status === 200) {
                this.authService.retrieveCurrentUser()
                    .subscribe(
                        resp => {
                            this.authService.setCurrentUser(resp.body);
                            if (this.authService.getCurrentUser()) {
                                this.router.navigate(['/']);
                            }
                        },
                        err => { if (err.status !== 401) { console.log(err); } }
                    );
                this.activityService.addActivity(Actions.LOGGED_IN).subscribe();
            }
        }
    );
  }

  onForgottenPass() {
    this.authService.isForgottenPassActive = !this.authService.isForgottenPassActive;
  }

  get email() { return this.loginForm.get('email'); }
  get password() { return this.loginForm.get('password'); }
}
