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
  
  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  forgotPassForm = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.email])
  })
  
  onForgotPassSubmit() {
    this.authService.forgotPassFormError = false;
    this.authService.sentNewUserPassword(this.forgotPassForm.value.email);
    this.forgotPassForm.reset();
  }

  onCancel() {
    this.forgotPassForm.reset();
    this.authService.forgotPassFormError = false;
    this.authService.forgottenPass = false;
    
  }

  get email() { return this.forgotPassForm.get('email'); }

}
