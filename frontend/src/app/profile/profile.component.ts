import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth/auth.service';
import { WindowPopService } from '../shared/window-pop/window-pop.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    windowPopSuccess = false;
    windowPop = false;
    windowPopFail = false;

  changePasswordForm = new FormGroup({
      oldPassword: new FormControl(null, [Validators.required, Validators.minLength(6)]),
      passwords: new FormGroup({
        newPassword: new FormControl(null, [Validators.required, Validators.minLength(6)]),
        repeatNewPassword: new FormControl(null, [Validators.required, Validators.minLength(6)])
      }, { validators: this.matchPasswords })
    }
  )

  constructor(public authService: AuthService,
                private windowPopService: WindowPopService) { }

    matchPasswords(control: AbstractControl): { invalid: boolean } {
        if (control.value.newPassword !== control.value.repeatNewPassword) {
            return {invalid: true};
        }
        return null;
    }

  ngOnInit() {
  }

  onSave() {
    this.authService.updateUser(this.oldPassword.value, 
        this.newPassword.value, this.repeatNewPassword.value) 
        .subscribe(
            resp => {
                this.windowPopService.title = "Authentication Successful";
                this.windowPopService.context = "Your request was successful.";
                this.windowPopService.details = "An new password will be sent to your email";
                this.windowPopService.activate = true;
            },
            error => {
                this.windowPopService.title = "Authentication Failed";
                this.windowPopService.context = "Your request is not successful!";
                this.windowPopService.details = "Try again with different credentials.";
                this.windowPopService.activate = true;
            }
        );
  }

  onCancel() {
      this.changePasswordForm.reset();
  }

  get oldPassword() { return this.changePasswordForm.get('oldPassword'); }
  get newPassword() { return this.changePasswordForm.controls.passwords.get('newPassword'); }
  get repeatNewPassword() { return this.changePasswordForm.controls.passwords.get('repeatNewPassword'); }
  get passwords() { return this.changePasswordForm.controls.passwords; }
}
