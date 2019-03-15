import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { ProductsService } from '../prototype/products/products.service';
import { ProjectsService } from '../prototype/projects/projects.service';
import { WindowPopService } from '../shared/window-pop/window-pop.service';
import { Statuses } from '../prototype/status.enum';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  inProgressProductsCount: number;
  newProjectsCount: number;

  constructor(public authService: AuthService,
              private productsService: ProductsService,
              private projectsService: ProjectsService,
              private windowPopService: WindowPopService) { }

  ngOnInit() {
    // IN_PROGRESS Products
    this.productsService.getProductsCountByStatusId(Statuses.IN_PROGRESS) 
      .subscribe(
        products => { 
          this.inProgressProductsCount = products;
        },
        error => { console.log(error) }
      );

    // NEW Projects
    this.projectsService.getProjectsCountByStatusId(Statuses.NEW) 
    .subscribe(
      projects => { 
        this.newProjectsCount = projects;
      },
      error => { console.log(error) }
    );
  }

  onLogout() {
    this.windowPopService.setTitle("Log out");
    this.windowPopService.setContext("Are you sure?");
    this.windowPopService.setDetails("You will be logged out!");
    this.windowPopService.activate();
    this.windowPopService.setLogout(true);
  }

}
