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

  forgotPassFormSubmited = false;
  forgotPassFormError = false;

  onCancel() {
    this.forgottenPassChange.emit();
    this.forgotPassForm.reset();
    this.forgotPassFormError = false;
  }
  
  constructor(public authService: AuthService) { }

  ngOnInit() {
  }

  forgotPassForm = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.email])
  })
  
  onForgotPassSubmit() {
    this.forgotPassFormError = false;
    this.authService.sentNewUserPassword(this.forgotPassForm.value.email)
        .subscribe(
            res => { 
                this.forgotPassFormSubmited = true;
                setTimeout(() => {
                    this.forgottenPass=false;
                    this.forgottenPassChange.emit();
                    this.forgotPassFormSubmited = false;
                }, 1200);
            },
            error => {
                console.log(error)
                this.forgotPassFormError = true;
            }
        );
    this.forgotPassForm.reset();
  }

  get email() { return this.forgotPassForm.get('email'); }
}
