import { Component, EventEmitter, Input, OnInit, Output, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { WindowPopService } from './window-pop.service';
import { ProductsService } from 'src/app/prototype/products/products.service';
import { Actions } from 'src/app/general/home/activity/action.enum';

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
    this.activityService.addActivity(Actions.LOGGED_OUT).subscribe(
            resp => { return this.authService.logout()
            .subscribe(
                res => {
                this.router.navigate(['/login']);
                this.windowPopService.deactivate();
                this.activeChange.emit(false);
                },
                error => {
                if (error.status === 404) {
                    this.authService.setCurrentUser(undefined);
                    this.router.navigate(['/login']);
                    this.windowPopService.deactivate();
                    this.activeChange.emit(false);
                }
                }
            );
        }
        );
    }

    onDeleteImage() {
        this.productService.deleteImageConfirmed.next(true);
        this.windowPopService.deactivate();
        this.activeChange.emit(false);
    }

    onAcceptCookies() {
        document.cookie = 'acceptedCookies=true';
        this.windowPopService.deactivate();
        this.activeChange.emit(false);
    }

    onAcceptCookiesToDB() {
        const tempUser = this.authService.getCurrentUser();
        tempUser.acceptedCookies = true;

        this.authService.acceptCookies().subscribe(
            res => {
                this.authService.setCurrentUser(tempUser);
            },
            error => console.log(error)
        );
        this.windowPopService.deactivate();
        this.activeChange.emit(false);
    }

    onCancel() {
        this.windowPopService.deactivate();
        this.activeChange.emit(false);
    }

    ngOnInit() {
    }

    ngOnDestroy() {
        this.windowPopService.deactivate();
    }

    get logout() { return this.windowPopService.isLogout(); }
    get deleteImage() { return this.windowPopService.isDeleteImage(); }
    get cookies() { return this.windowPopService.isCookies(); }
    get cookiesToDB() { return this.windowPopService.isCookiesToDB(); }
    get title() { return this.windowPopService.getTitle(); }
    get context() { return this.windowPopService.getContext(); }
    get details() { return this.windowPopService.getDetails(); }
}
