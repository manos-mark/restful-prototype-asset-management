import { Component, OnInit, OnChanges } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { WindowPopService } from './shared/window-pop/window-pop.service';
import { ImageCarouselService } from './shared/image-carousel/image-carousel.service';
import { BreadcrumbsService } from './shared/breadcrumbs.service';
import { NotificationService } from './shared/notification/notification.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    constructor(private authService: AuthService,
                private windowPopService: WindowPopService,
                private carouselService: ImageCarouselService,
                private breadcrumbsService: BreadcrumbsService,
                private notificationService: NotificationService,
                private router: Router) {
        if (!document.cookie.match('acceptedCookies')) {
            this.windowPopService.setTitle('Cookies');
            this.windowPopService.setContext('This site uses cookies');
            this.windowPopService.setDetails('Do you accept?');
            this.windowPopService.setCookies(true);
            this.windowPopService.activate();
        }
    }

    ngOnInit() {
        this.authService.retrieveCurrentUser()
            .subscribe(
                resp => {
                    this.authService.setCurrentUser(resp.body);
                    if (this.authService.getCurrentUser()) {
                        this.router.navigate(['/']);
                    }
                },
                error => { console.log(error); }
            );
    }

    get carouselAvtivate() { return this.carouselService.activate; }
    get windowPopAvtivate() { return this.windowPopService.isActivated(); }
    get notificationAvtivate() { return this.notificationService.isActivated(); }
    get currentUser() { return this.authService.getCurrentUser(); }
    get breadcrumbs() { return this.breadcrumbsService.breadcrumbs; }
}
