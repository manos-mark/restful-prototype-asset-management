import { Component, OnInit } from '@angular/core';
import { SearchService } from './search.service';
import { ProductsService } from 'src/app/prototype/products/products.service';
import { ProjectsService } from 'src/app/prototype/projects/projects.service';
import { Router } from '@angular/router';
import { SearchProduct } from './search-product.model';
import { SearchProject } from './search-project.model';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
    constructor(private searchService: SearchService,
                private productService: ProductsService,
                private projectService: ProjectsService,
                private router: Router) { }

    ngOnInit() {
    }

    onInput(input) {
        if (!input.value.match('').index) {
            if (input.value.length >= 3) {
                this.searchService.search(input.value)
                    .subscribe (
                        res => {
                            this.searchService.products = res.products;
                            this.searchService.projects = res.projects;
                        },
                        error => console.log(error)
                    );
            }
            this.searchService.clear();
        } else {
            this.onClear(input);
        }
    }

    onEditProduct(productId: number, input) {
        this.productService.editMode = true;
        this.router.navigate(['prototype/products/', productId, 'edit'],
            {queryParams: { productId: productId }}
        );
        this.onClear(input);
    }

    onEditProject(projectId: number, input) {
        this.projectService.editMode = true;
        this.router.navigate(['prototype/projects/', projectId, 'edit'],
            {queryParams: { projectId: projectId }}
        );
        this.onClear(input);
    }

    onClear(input) {
        this.searchService.clear();
        input.value = '';
    }

    get products() { return this.searchService.products; }
    get projects() { return this.searchService.projects; }
}
