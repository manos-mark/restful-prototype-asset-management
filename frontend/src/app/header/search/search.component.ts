import { Component, OnInit } from '@angular/core';
import { SearchService } from './search.service';
import { ProductsService } from 'src/app/prototype/products/products.service';
import { ProjectsService } from 'src/app/prototype/projects/projects.service';
import { Router } from '@angular/router';
import { SearchProduct } from './search-product.model';
import { SearchProject } from './search-project.model';
import { SearchItem } from './search-item.model';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
    private index = -1;
    constructor(private searchService: SearchService,
                private productService: ProductsService,
                private projectService: ProjectsService,
                private router: Router) { }

    ngOnInit() {
    }

    onKeyup(input) {
        if (event['key'] === 'ArrowDown') {
            this.moveDown();
        } else if (event['key'] === 'ArrowUp') {
            this.moveUp();
        } else if (event['key'] === 'Enter') {
            this.select(input);
        } else {
            this.searchService.input = input;
            if (!input.value.match('').index) {
                if (input.value.length >= 3) {
                    this.searchService.search(input.value)
                        .subscribe (
                            res => {
                                this.index = -1;
                                this.searchService.itemsArray = [];
                                res.products.forEach(item => {
                                    this.searchService.itemsArray.push(new SearchItem(item));
                                });
                                res.projects.forEach(item => {
                                    this.searchService.itemsArray.push(new SearchItem(item));
                                });
                            },
                            error => console.log(error)
                        );
                } else {
                    this.searchService.itemsArray = [];
                }
            } else {
                this.onClear(input);
            }
        }
    }

    onEdit(productId: number, projectId: number, input) {
        if (productId) {
            this.productService.editMode = true;
            this.router.navigate(['prototype/products/', productId, 'edit'],
                {queryParams: { productId: productId }}
            );
        } else if (projectId) {
            this.projectService.editMode = true;
            this.router.navigate(['prototype/projects/', projectId, 'edit'],
                {queryParams: { projectId: projectId }}
            );
        }
        this.onClear(input);
    }

    onClear(input) {
        this.searchService.clear();
    }

    moveDown() {
        if (this.itemsArray[this.index + 1]) {
            if (this.itemsArray[this.index]) {
                this.itemsArray[this.index].isHovered = false;
            }
            this.itemsArray[++this.index].isHovered = true;
        } else if (this.itemsArray[this.itemsArray.length - 1]) {
            this.itemsArray[this.itemsArray.length - 1].isHovered = false;
            this.itemsArray[0].isHovered = true;
            this.index = 0;
        }
    }

    moveUp() {
        if (this.itemsArray[this.index - 1]) {
            if (this.itemsArray[this.index]) {
                this.itemsArray[this.index].isHovered = false;
            }
            this.itemsArray[--this.index].isHovered = true;
        } else if (this.itemsArray[this.itemsArray.length - 1]) {
            this.itemsArray[0].isHovered = false;
            this.itemsArray[this.itemsArray.length - 1].isHovered = true;
            this.index = this.itemsArray.length - 1;
        }
    }

    select(input) {
        if (this.itemsArray[this.index]) {
            const item = this.itemsArray[this.index];
            if (item.product) {
                this.itemsArray[this.index].isHovered = false;
                this.itemsArray[this.index].isSelected = true;
                this.onEdit(item.product.id, 0, input);
            } else if (item.project) {
                this.itemsArray[this.index].isHovered = false;
                this.itemsArray[this.index].isSelected = true;
                this.onEdit(0, item.project.id, input);
            }
        }
    }

    get itemsArray() { return this.searchService.itemsArray; }
}
