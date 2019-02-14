import { Component, OnInit } from '@angular/core';
import { ProductsService } from 'src/app/prototype/products/products.service';
import { ProjectsService } from 'src/app/prototype/projects/projects.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  newProductsCount: number;
  inProgressProductsCount: number;
  finishedProductsCount: number;
  newProjectsCount: number;
  inProgressProjectsCount: number;
  finishedProjectsCount: number;

  constructor(private productsService: ProductsService,
              private projectsService: ProjectsService) { }

  ngOnInit() {
    // NEW Products
    this.productsService.getProductsCountByStatusId(2) 
      .subscribe(
        products => { 
          this.newProductsCount = products;
        },
        error => { console.log(error) }
      );

    // IN_PROGRESS Products
    this.productsService.getProductsCountByStatusId(1) 
      .subscribe(
        products => { 
          this.inProgressProductsCount = products;
        },
        error => { console.log(error) }
      );

    // FINISHED Products
    this.productsService.getProductsCountByStatusId(3) 
      .subscribe(
        products => { 
          this.finishedProductsCount = products;
        },
        error => { console.log(error) }
      );

    // NEW Products
    this.productsService.getProductsCountByStatusId(2) 
      .subscribe(
        products => { 
          this.newProductsCount = products;
        },
        error => { console.log(error) }
      );

    // IN_PROGRESS Products
    this.productsService.getProductsCountByStatusId(1) 
      .subscribe(
        products => { 
          this.inProgressProductsCount = products;
        },
        error => { console.log(error) }
      );

    // FINISHED Products
    this.productsService.getProductsCountByStatusId(3) 
      .subscribe(
        products => { 
          this.finishedProductsCount = products;
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

    // IN_PROGRESS Projects
    this.projectsService.getProjectsCountByStatusId(1) 
      .subscribe(
        projects => { 
          this.inProgressProjectsCount = projects;
        },
        error => { console.log(error) }
    );

    // FINISHED Products
    this.projectsService.getProjectsCountByStatusId(3) 
      .subscribe(
        projects => { 
          this.finishedProjectsCount = projects;
        },
        error => { console.log(error) }
    );
  }

}
