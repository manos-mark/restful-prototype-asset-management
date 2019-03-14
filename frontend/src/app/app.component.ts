import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { Router } from '@angular/router';
import { WindowPopService } from './shared/window-pop/window-pop.service';
import { ImageCarouselService } from './shared/image-carousel/image-carousel.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(public authService: AuthService,
    public router: Router,
    public windowPopService: WindowPopService,
    public carouselService: ImageCarouselService) {}

  ngOnInit() {
  }
}
