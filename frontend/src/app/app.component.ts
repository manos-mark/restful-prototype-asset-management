import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { WindowPopService } from './shared/window-pop/window-pop.service';
import { ImageCarouselService } from './shared/image-carousel/image-carousel.service';
import { BreadcrumbsService } from './shared/breadcrumbs.service';
import { NotificationService } from './shared/notification/notification.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(public authService: AuthService,
    private windowPopService: WindowPopService,
    private carouselService: ImageCarouselService,
    private breadcrumbsService: BreadcrumbsService,
    private notificationService: NotificationService) {}

  ngOnInit() {
  }

  get carouselAvtivate() { return this.carouselService.activate }
  get windowPopAvtivate() { return this.windowPopService.isActivated() }
  get notificationAvtivate() { return this.notificationService.isActivated() }
  get isAuthenticated() { return this.authService.isAuthenticated() }
  get breadcrumbs() { return this.breadcrumbsService.breadcrumbs }
}
