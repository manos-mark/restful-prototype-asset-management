import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth/auth.service';
import { WindowPopService } from '../shared/window-pop/window-pop.service';
import { BreadcrumbsService } from '../shared/breadcrumbs.service';
import { NotificationService } from '../shared/notification/notification.service';
import { SearchService } from '../header/search/search.service';

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
    );

    constructor(public authService: AuthService,
                private windowPopService: WindowPopService,
                private breadcrumbsService: BreadcrumbsService,
                private notificationService: NotificationService,
                private searchService: SearchService) {
        this.breadcrumbsService.setBreadcrumbsProfile();
    }

    matchPasswords(control: AbstractControl): { invalid: boolean } {
        if (control.value.newPassword !== control.value.repeatNewPassword) {
            return {invalid: true};
        }
        if (control.value.newPassword === control.value.oldPassword) {
            return {invalid: true};
        }
        if (control.value.repeatNewPassword === control.value.oldPassword) {
            return {invalid: true};
        }
        return null;
    }

    ngOnInit() {
        this.searchService.clear();
    }

    onSave() {
        this.authService.updateUser(this.oldPassword.value,
            this.newPassword.value, this.repeatNewPassword.value)
            .subscribe(
                resp => {
                    this.notificationService.showNotification();
                    this.onCancel();
                },
                error => {
                    this.windowPopService.setTitle('Authentication Failed');
                    this.windowPopService.setContext('Your request is not successful!');
                    this.windowPopService.setDetails('Try again with different credentials.');
                    this.windowPopService.activate();
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
