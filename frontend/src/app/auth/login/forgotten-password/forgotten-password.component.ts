import { Component, OnInit, Input, OnDestroy, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../../auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-forgotten-password',
  templateUrl: './forgotten-password.component.html',
  styleUrls: ['./forgotten-password.component.css']
})
export class ForgottenPasswordComponent implements OnInit {
  @Input() forgottenPass: Boolean;
  @Output() forgottenPassChange = new EventEmitter<string>();

  EMAIL_PATTERN = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  forgotPassFormSubmited = false;
  forgotPassFormError = false;

  forgotPassForm = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.pattern(this.EMAIL_PATTERN)])
  });

  onCancel() {
    this.forgottenPassChange.emit();
    this.forgotPassForm.reset();
    this.forgotPassFormError = false;
  }

  constructor(public authService: AuthService) { }

  ngOnInit() {
  }


  onForgotPassSubmit() {
    this.forgotPassFormError = false;
    this.authService.sentNewUserPassword(this.forgotPassForm.value.email)
        .subscribe(
            res => {
                this.forgotPassFormSubmited = true;
                setTimeout(() => {
                    this.forgottenPass = false;
                    this.forgottenPassChange.emit();
                    this.forgotPassFormSubmited = false;
                }, 1200);
            },
            error => {
                console.log(error);
                this.forgotPassFormError = true;
                setTimeout(() => {
                    this.forgotPassFormError = false;
                }, 1200);
            }
        );
    this.forgotPassForm.reset();
  }

  get email() { return this.forgotPassForm.get('email'); }
}
