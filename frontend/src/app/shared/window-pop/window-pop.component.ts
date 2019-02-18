import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { ActivityService } from 'src/app/general/home/activity/activity.service';

@Component({
  selector: 'app-window-pop',
  templateUrl: './window-pop.component.html',
  styleUrls: ['./window-pop.component.css']
})
export class WindowPopComponent implements OnInit {
  @Input() windowPopFail: Boolean;
  @Input() windowPopSuccess: Boolean;
  @Input() windowPopLogout: Boolean;
  @Input() active: Boolean;
  @Output() activeChange = new EventEmitter<string>();
  
  constructor(public authService: AuthService,
              public activityService: ActivityService,
              public router: Router) { }

  onSubmit() {
    this.activityService.addActivity('8').subscribe(
          resp => { return this.authService.logout()
            .subscribe(
              res => {
                this.router.navigate(['/login']);
                this.activeChange.emit();
              },
              error => {
                if (error.status === 404) {
                  this.authService.currentUser = undefined;
                  this.router.navigate(['/login']);
                  this.activeChange.emit();
                }
              }
            ) }
        );
  }

  onCancel() {
    this.activeChange.emit(); 
  }

  ngOnInit() {
  }

}
