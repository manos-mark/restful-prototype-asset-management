import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { WindowPopService } from './window-pop.service';

@Component({
  selector: 'app-window-pop',
  templateUrl: './window-pop.component.html',
  styleUrls: ['./window-pop.component.css']
})
export class WindowPopComponent implements OnInit {
  @Input() active: Boolean;
  @Output() activeChange = new EventEmitter<string>();
  
  constructor(private authService: AuthService,
                private activityService: ActivityService,
                private router: Router,
                private windowPopService: WindowPopService) { }

  onSubmit() {
    this.activityService.addActivity('8').subscribe(
          resp => { return this.authService.logout()
            .subscribe(
              res => {
                this.router.navigate(['/login']);
                this.windowPopService.logout = false;
                this.activeChange.emit();
              },
              error => {
                if (error.status === 404) {
                  this.authService.currentUser = undefined;
                  this.router.navigate(['/login']);
                  this.windowPopService.logout = false;
                  this.activeChange.emit();
                }
              }
            ) }
        );
  }

  onCancel() {
    this.windowPopService.logout = false;
    this.activeChange.emit(); 
  }

  ngOnInit() {
  }

  get logout() { return this.windowPopService.logout }
  get title() { return this.windowPopService.title }
  get context() { return this.windowPopService.context }
  get details() { return this.windowPopService.details }
}
