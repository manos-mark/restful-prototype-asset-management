import { Component, OnInit, Input, OnDestroy, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../../auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-forgotten-password',
  templateUrl: './forgotten-password.component.html',
  styleUrls: ['./forgotten-password.component.css']
})
export class ForgottenPasswordComponent implements OnInit, OnDestroy {
  @Input() forgottenPass: Boolean;
  @Output() forgottenPassChanged = new EventEmitter<Boolean>();
  subscription: Subscription;
  submited = false;

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  forgotPassForm = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.email])
  })
  
  onForgotPassSubmit() {
    this.submited = true;
    this.subscription = this.authService.sentNewUserPassword(this.forgotPassForm.value.email)
    .subscribe(
      res => console.log(res),
      error => console.log(error)
    );
  }

  onCancel() {
    this.forgottenPassChanged.emit(this.forgottenPass = false)
  }

  get email() { return this.forgotPassForm.get('email'); }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}
