import { Component, EventEmitter, Input, OnInit, Output, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { WindowPopService } from './window-pop.service';
import { ProductsService } from 'src/app/prototype/products/products.service';

@Component({
  selector: 'app-window-pop',
  templateUrl: './window-pop.component.html',
  styleUrls: ['./window-pop.component.css']
})
export class WindowPopComponent implements OnInit, OnDestroy {
  @Input() active: Boolean;
  @Output() activeChange = new EventEmitter<Boolean>();
  
  constructor(private authService: AuthService,
                private activityService: ActivityService,
                private router: Router,
                private windowPopService: WindowPopService,
                private productService: ProductsService) { }

  onLogout() {
    this.activityService.addActivity('8').subscribe(
          resp => { return this.authService.logout()
            .subscribe(
              res => {
                this.router.navigate(['/login']);
                this.windowPopService.logout = false;
                this.activeChange.emit(false);
              },
              error => {
                if (error.status === 404) {
                  this.authService.currentUser = undefined;
                  this.router.navigate(['/login']);
                  this.windowPopService.logout = false;
                  this.activeChange.emit(false);
                }
              }
            ) }
        );
  }

  onDeleteImage() {
    this.productService.deleteImageConfirmed.next(true);
    this.windowPopService.deleteImage = false;
    this.activeChange.emit(false);
  }

  onAcceptCookies() {
    document.cookie = 'acceptedCookies=true'
    this.windowPopService.cookies = false;
    this.activeChange.emit(false);
  }

  onCancel() {
    this.windowPopService.deleteImage = false;
    this.windowPopService.logout = false;
    this.windowPopService.cookies = false;
    this.activeChange.emit(false); 
  }

  ngOnInit() {
  }

  ngOnDestroy() {
    this.windowPopService.deleteImage = false;
    this.windowPopService.logout = false;
    this.windowPopService.cookies = false;
  }

  get logout() { return this.windowPopService.logout }
  get deleteImage() { return this.windowPopService.deleteImage }
  get cookies() { return this.windowPopService.cookies }
  get title() { return this.windowPopService.title }
  get context() { return this.windowPopService.context }
  get details() { return this.windowPopService.details }
}
