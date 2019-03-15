import { Component, OnInit } from '@angular/core';
import { SearchService } from './search.service';
import { Project } from 'src/app/prototype/projects/project.model';
import { Product } from 'src/app/prototype/products/product.model';
import { ProductsService } from 'src/app/prototype/products/products.service';
import { ProjectsService } from 'src/app/prototype/projects/projects.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
    products: Product[] = [];
    projects: Project[] = [];

    constructor(private searchService: SearchService,
                private productService: ProductsService,
                private projectService: ProjectsService,
                private router: Router) { }

    ngOnInit() {
    }

    onInput(input) {
        if (!input.value.match('').index){
            this.searchService.search(input.value)
                .subscribe (
                    res => {
                        console.log(res)
                        this.products = res.products;
                        this.projects = res.projects;
                    },
                    error => console.log(error)
                )
        }
        else {
            this.products = [];
            this.projects = [];
            input.value = "";
        }
    }

    onEditProduct(productId: number, input) {
        this.productService.editMode = true;
        this.router.navigate(['prototype/products/', productId, 'edit'], 
            {queryParams: { productId: productId }}
        );
        input.value = '';
    }

    onEditProject(projectId: number, input) {
        this.projectService.editMode = true;
        this.router.navigate(['prototype/projects/', projectId, 'edit'], 
            {queryParams: { projectId: projectId }}
        );
        input.value = '';
    }

}
