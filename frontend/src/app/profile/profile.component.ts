import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { FormGroup, FormControl, Validators, ValidatorFn } from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  changePasswordForm = new FormGroup({
      oldPassword: new FormControl(null, [Validators.required, Validators.minLength(6)]),
      newPassword: new FormControl(null, [Validators.required, Validators.minLength(6), this.matchPasswords()]),
      repeatNewPassword: new FormControl(null, [Validators.required, Validators.minLength(6), this.matchPasswords()])
    }
  )

  constructor(public authService: AuthService) { }

  ngOnInit() {
  }

  matchPasswords() { 
    let pass = this.changePasswordForm.controls.newPassword.value;
    let confirmPass = this.changePasswordForm.controls.repeatNewPassword.value;

    return pass === confirmPass ? null : { valid: true }     
  }

  onSave() {
    this.authService.updateUser(
      this.changePasswordForm.value.oldPassword, 
      this.changePasswordForm.value.newPassword, 
      this.changePasswordForm.value.repeatNewPassword
    ) 
  }

  onCancel() {
      this.changePasswordForm.reset();
      // this.authService.changePasswordError = false;
      // this.authService.changePassword = false;
  }

  get oldPassword() { return this.changePasswordForm.get('oldPassword'); }
  get newPassword() { return this.changePasswordForm.get('newPassword'); }
  get repeatNewPassword() { return this.changePasswordForm.get('repeatNewPassword'); }
  
}
