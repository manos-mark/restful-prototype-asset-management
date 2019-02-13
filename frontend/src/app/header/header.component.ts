import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { ProductsService } from '../prototype/products/products.service';
import { ProjectsService } from '../prototype/projects/projects.service';

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
              private projectsService: ProjectsService) { }

  ngOnInit() {
    // IN_PROGRESS Products
    this.productsService.getProductsCountByStatusId(1) 
      .subscribe(
        products => { 
          this.inProgressProductsCount = products;
        },
        error => { console.log(error) }
      );

    // NEW Projects
    this.projectsService.getProjectsCountByStatusId(2) 
    .subscribe(
      projects => { 
        this.newProjectsCount = projects;
      },
      error => { console.log(error) }
    );
  }

  onLogout() {
    this.authService.windowPopLogout = true;
    this.authService.windowPop = true;
  }

}
