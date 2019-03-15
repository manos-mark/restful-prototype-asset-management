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
        this.breadcrumbsService.setBreadcrumbsGeneral();
    }

  ngOnInit() {
    if (!this.authService.getCurrentUser().acceptedCookies) {
        this.windowPopService.setTitle("Cookies");
        this.windowPopService.setContext("This site uses cookies");
        this.windowPopService.setDetails("Do you accept?");
        this.windowPopService.setCookiesToDB(true);
        this.windowPopService.activate();
    }
  }

}
