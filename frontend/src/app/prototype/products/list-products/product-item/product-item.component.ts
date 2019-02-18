import { Component, OnInit, Input } from '@angular/core';
import { Product } from '../../product.model';
import { ProductsService } from '../../products.service';
import { Router } from '@angular/router';
import { ProjectsService } from 'src/app/prototype/projects/projects.service';

@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css']
})
export class ProductItemComponent implements OnInit {
  @Input() product: Product;
  projectName: string = "";

  constructor(private productService: ProductsService,
                private router: Router,
                private projectService: ProjectsService) { }

  ngOnInit() {
    this.projectService.getProjectName(this.product.projectId)
        .subscribe(
            res => this.projectName = res.projectName,
            error => console.log(error)
        );
  }

  onEdit() {
    this.productService.editMode = true;
    this.router.navigate(['prototype/products/' + this.product.id + '/edit']);
  }

}
