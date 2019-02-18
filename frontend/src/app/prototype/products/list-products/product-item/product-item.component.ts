import { Component, OnInit, Input } from '@angular/core';
import { Product } from '../../product.model';
import { ProductsService } from '../../products.service';
import { Router } from '@angular/router';
import { ProjectsService } from 'src/app/prototype/projects/projects.service';
import { ProductPicture } from '../../product-picture.model';

@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css']
})
export class ProductItemComponent implements OnInit {
  @Input() product: Product;
  thumbPicture: ProductPicture;
  pictures: ProductPicture[];
  projectName: string = "";
  picturesLength: number;

  constructor(private productService: ProductsService,
                private router: Router,
                private projectService: ProjectsService) { }

  ngOnInit() {
    this.projectService.getProjectName(this.product.projectId)
        .subscribe(
            res => this.projectName = res.projectName,
            error => console.log(error)
        );
    this.productService.getThumbPictureByProductId(this.product.id)
        .subscribe(
            res => {this.thumbPicture = res; console.log(this.thumbPicture)},
            error => console.log(error)
        );
    this.productService.getPicturesCountByProductId(this.product.id)
        .subscribe(
            res => this.picturesLength = res,
            error => console.log(error)
        );
  }

  onEdit() {
    this.productService.editMode = true;
    this.router.navigate(['prototype/products/' + this.product.id + '/edit']);
  }

  showCarousel() {
    this.productService.getPicturesByProductId(this.product.id)
        .subscribe(
            res => {this.pictures = res; console.log(this.pictures)},
            error => console.log(error)
        );
  }

}
