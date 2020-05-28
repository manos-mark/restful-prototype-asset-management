import { Component, OnInit, OnChanges, OnDestroy } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { WindowPopService } from './shared/window-pop/window-pop.service';
import { ImageCarouselService } from './shared/image-carousel/image-carousel.service';
import { BreadcrumbsService } from './shared/breadcrumbs.service';
import { NotificationService } from './shared/notification/notification.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LoaderService } from './shared/loader/loader.service';
import { LoaderState } from './shared/loader/loader';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
    // showLoader = false;
    // private subscription: Subscription;

    constructor(private authService: AuthService,
                private windowPopService: WindowPopService,
                private carouselService: ImageCarouselService,
                private breadcrumbsService: BreadcrumbsService,
                private notificationService: NotificationService,
                private loaderService: LoaderService) {
        if (!document.cookie.match('acceptedCookies')) {
            this.windowPopService.setTitle('Cookies');
            this.windowPopService.setContext('This site uses cookies');
            this.windowPopService.setDetails('Do you accept?');
            this.windowPopService.setCookies(true);
            this.windowPopService.activate();
        }
    }

    ngOnInit() {
        // this.subscription = this.loaderService.loaderState
        //     .subscribe((state: LoaderState) => {
        //             this.showLoader = state.show;
        //         });
    }

    ngOnDestroy() {
        // this.subscription.unsubscribe();
    }

    get carouselAvtivate() { return this.carouselService.activate; }
    get windowPopAvtivate() { return this.windowPopService.isActivated(); }
    get notificationAvtivate() { return this.notificationService.isActivated(); }
    get currentUser() { return this.authService.getCurrentUser(); }
    get isForgottenPassActive() { return this.authService.isForgottenPassActive; }
    get breadcrumbs() { return this.breadcrumbsService.breadcrumbs; }
}
