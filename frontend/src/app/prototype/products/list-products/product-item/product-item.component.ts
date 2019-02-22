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
  @Input() inProject: Boolean = false;
  thumbPictureUrl: URL;
  pictures: URL[];
//   projectName: string = "";
//   picturesLength: number;

  constructor(private productService: ProductsService,
                private router: Router,
                private projectService: ProjectsService) { }

  ngOnInit() {
  }

  onEdit() {
    this.productService.editMode = true;
    this.router.navigate(['prototype/products/' + this.product.id + '/edit']);
  }

  getProductPictures() {
    this.productService.getPicturesByProductId(this.product.id)
        .subscribe(
            res => {this.pictures = res; console.log(this.pictures)},
            error => console.log(error)
        );
  }

}
