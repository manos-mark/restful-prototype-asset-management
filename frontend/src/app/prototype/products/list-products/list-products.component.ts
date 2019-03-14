import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { forkJoin, Observable } from 'rxjs';
import { ImageCarouselService } from 'src/app/shared/image-carousel/image-carousel.service';
import { FilterParams } from '../../projects/filter-params.model';
import { PageParams } from '../../projects/page-params.model';
import { Statuses } from '../../status.enum';
import { Product } from '../product.model';
import { ProductsService } from '../products.service';
import { ProductPicture } from '../../product-picture.model';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { Actions } from 'src/app/general/home/activity/action.enum';

@Component({
  selector: 'app-list-products',
  templateUrl: './list-products.component.html',
  styleUrls: ['./list-products.component.css']
})
export class ListProductsComponent implements OnInit {
    products: Product[] = [];
    pictures: ProductPicture[] = [];
    projectsNames: string[] = [];
    filterParams = new FilterParams;
    pageParams = new PageParams;
    sortByDateAsc = true;
    sortByQuantityAsc = true;
    isMasterChecked: boolean = false;
    totalCount: number;
    selectedProductsCount = 0;
    pagesArray = [];
    totalPages = this.products.length / this.pageParams.pageSize;
  
    constructor(private productService: ProductsService,
                private router: Router,
                private route: ActivatedRoute,
                private carouselService: ImageCarouselService,
                private activityService: ActivityService) { }

    ngOnInit() {
        this.route.queryParams.subscribe(
            res => { this.filterParams.projectName = res.projectName }
        )
        this.isMasterChecked = false;
        this.productService.getProducts(this.pageParams, this.filterParams)
                .subscribe(
                    res => {
                        res['items'].map(
                            item => {
                                this.products.push(new Product(item));
                                if (!this.projectsNames.includes(item.projectName)){
                                    this.projectsNames.push(item.projectName); 
                                }
                            }
                        )
                        this.totalCount = res['totalCount'];
                        this.totalPages = Math.ceil(this.totalCount / this.pageParams.pageSize);
                        this.pagesArray =  Array(this.totalPages).fill(1).map((x,i)=>++i);
                    },
                    error => console.log(error)
                )
    }

    onEdit(productId: number) {
        this.productService.editMode = true;
        this.router.navigate(['prototype/products/', productId, 'edit'], 
            {queryParams: { productId: productId }}
        );
    }

    onSelect(product: Product) {
        if (product.isChecked) {
            --this.selectedProductsCount;
        } else {
            ++this.selectedProductsCount;
        }
        product.isChecked = !product.isChecked; 
        this.isMasterChecked = false;
    }

    onSelectAll() {
        let productsCount = 0;
        for (let product of this.products) {
            product.isChecked = !this.isMasterChecked;
            productsCount++;
        }
        this.isMasterChecked = !this.isMasterChecked;
        
        if (this.isMasterChecked) {
            this.selectedProductsCount = productsCount;
        } else {
            this.selectedProductsCount = 0;
        }
    }

    applyChanges(action) {
        let selectedStatus: number;
        if (action == "NEW") {
            selectedStatus = Statuses.NEW;
        } 
        else if (action == "IN_PROGRESS") {
            selectedStatus = Statuses.IN_PROGRESS;
        }
        else if (action == "FINISHED") {
            selectedStatus = Statuses.FINISHED;
        }
        else if (action == "DELETE") {
            selectedStatus = null;
        } 
        else {
            return;
        }

        this.changeStatus(selectedStatus)
            .subscribe(
                dataArray => {
                    this.products = new Array();
                    this.isMasterChecked = false;
                    this.ngOnInit();
                },
                error => console.log(error)
            );
    }
    
    changeStatus(selectedStatus: number) {
        let observables: Observable<any>[] = new Array();
        
        this.products.forEach(
            (product) => {
                if ( product.isChecked) {
                    // delete
                    if (selectedStatus == null) {
                        observables.push(this.productService.deleteProduct(product.id));
                        observables.push(this.activityService.addActivity(Actions.DELETED_PRODUCT));
                    }
                    // change status
                    else {
                        product.status.id = selectedStatus;
                        observables.push(this.productService.updateProductStatus(product));
                        observables.push(this.activityService.addActivity(Actions.UPDATED_PRODUCT));
                    }
                }
            }
        )

        return forkJoin(observables);
    }

    sortByDate() {
        this.sortByDateAsc = !this.sortByDateAsc;
        this.products = [];
        this.pageParams.field = 'date';
        if (this.sortByDateAsc) {
            this.pageParams.direction = 'asc';
        } else {
            this.pageParams.direction = 'desc';
        }
        this.ngOnInit();
    }

    sortByQuantity() {
        this.sortByQuantityAsc = !this.sortByQuantityAsc;
        this.products = [];
        this.pageParams.field = 'quantity';
        if (this.sortByQuantityAsc) {
            this.pageParams.direction = 'asc';
        } else {
            this.pageParams.direction = 'desc';
        }
        this.ngOnInit();
    }

    changeResultsPerPage(resultsPerPage: number) {
        this.products = new Array();
        this.pageParams.pageSize = resultsPerPage;
        this.pageParams.page = 1;
        this.ngOnInit();
    }

    changePageUp() {
        if (this.pageParams.page < this.totalPages) {
            this.products = new Array();
            this.pageParams.page ++;
            this.ngOnInit();
        }
    }

    changePageDown() {
        if (this.pageParams.page > 1) {
            this.products = new Array();
            this.pageParams.page --;
            this.ngOnInit();
        }
    }

    changePage(target: number) {
        if (target >= 1 && target <= this.totalPages && target !== this.pageParams.page) {
            this.products = new Array();
            this.pageParams.page = target;
            this.ngOnInit();
        }
    }

    applyFilters(statusId: number, projectName: string, dateFrom: Date, dateTo: Date) {
        
        this.filterParams.fromDate = dateFrom;
        this.filterParams.toDate = dateTo;

        if (projectName.match("null")) {
            this.filterParams.projectName = "";
        } else {
            this.filterParams.projectName = projectName;
        }

        if(statusId >= 1 && statusId <=3) {
            this.products = new Array();
            this.filterParams.statusId = statusId;
            this.ngOnInit();
        } else {
            this.products = new Array();
            this.filterParams.statusId = null;
            this.ngOnInit();
        }
    }

    clearFilters() {
        this.router.navigateByUrl('/', {skipLocationChange: true})
            .then(()=>
                this.router.navigate(['prototype/products/'])
            );
    }
    
    getProductPictures(productId: number) {
        this.productService.getPicturesByProductId(productId)
            .subscribe(
                res => {
                    this.pictures = [];
                    res.map( picture => {
                        this.pictures.push(new ProductPicture(picture));
                    })
                    this.carouselService.pictures = this.pictures;
                    this.carouselService.activate = true;
                },
                error => console.log(error)
            );
    }

}
