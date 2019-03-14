import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { WindowPopService } from 'src/app/shared/window-pop/window-pop.service';
import { BreadcrumbsService } from 'src/app/shared/breadcrumbs.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private authService: AuthService,
            private windowPopService: WindowPopService,
            private breadcrumbsService: BreadcrumbsService) {

        this.breadcrumbsService.breadcrumbs = [];
        this.breadcrumbsService.breadcrumbs.push({
            name: "General",
            src: "general"
        });
    }

  ngOnInit() {
    if (!this.authService.currentUser.acceptedCookies) {
        this.windowPopService.title = "Cookies";
        this.windowPopService.context = "This site uses cookies";
        this.windowPopService.details = "Do you accept?";
        this.windowPopService.cookiesToDB = true;
        this.windowPopService.activate = true;
    }
  }

}
