import { Component, OnInit } from '@angular/core';
import { ProductsService } from '../products.service';
import { Product } from '../product.model';

@Component({
  selector: 'app-list-products',
  templateUrl: './list-products.component.html',
  styleUrls: ['./list-products.component.css']
})
export class ListProductsComponent implements OnInit {
  products: Product[];
  totalCount: number;
  
  constructor(private productService: ProductsService) { }

  ngOnInit() {
    this.productService.getProducts(2,'date',1,5,'asc')
      .subscribe(
        res => {this.products = res['items']; this.totalCount = res['totalCount']},
        error => console.log(error)
      )
  }

}
