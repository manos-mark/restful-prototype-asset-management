import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { forkJoin, Observable, Subscription } from 'rxjs';
import { ImageCarouselService } from 'src/app/shared/image-carousel/image-carousel.service';
import { FilterParams } from '../../projects/filter-params.model';
import { PageParams } from '../../projects/page-params.model';
import { Statuses } from '../../status.enum';
import { Product } from '../product.model';
import { ProductsService } from '../products.service';
import { ProductPicture } from '../../product-picture.model';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { Actions } from 'src/app/general/home/activity/action.enum';
import { BreadcrumbsService } from 'src/app/shared/breadcrumbs.service';
import { NotificationService } from 'src/app/shared/notification/notification.service';
import { SearchService } from 'src/app/header/search/search.service';
import { WindowPopService } from 'src/app/shared/window-pop/window-pop.service';

@Component({
  selector: 'app-list-products',
  templateUrl: './list-products.component.html',
  styleUrls: ['./list-products.component.css']
})
export class ListProductsComponent implements OnInit, OnDestroy {
    products: Product[] = [];
    pictures: ProductPicture[] = [];
    projectsNames: string[] = [];
    sortByDateDesc = true;
    sortByQuantityDesc = false;
    isMasterChecked = false;
    totalCount: number;
    selectedProductsCount = 0;
    pagesArray = [];
    totalPages = this.products.length / this.pageParams.pageSize;
    deleteProductsSubscription: Subscription = null;

    constructor(private productService: ProductsService,
                private router: Router,
                private route: ActivatedRoute,
                private carouselService: ImageCarouselService,
                private activityService: ActivityService,
                private breadcrumbsService: BreadcrumbsService,
                private notificationService: NotificationService,
                private searchService: SearchService,
                private windowPopService: WindowPopService) {
        this.breadcrumbsService.setBreadcrumbsProducts();
    }

    ngOnInit() {
        this.selectedProductsCount = 0;
        this.searchService.clear();
        this.route.queryParams.subscribe(
            res => {
                if (res.projectName) {
                    this.filterParams.projectName = res.projectName;
                }
            }
        );
        this.isMasterChecked = false;
        this.productService.getProducts(this.pageParams, this.filterParams)
                .subscribe(
                    res => {
                        res['items'].map(
                            item => {
                                this.products.push(new Product(item));
                            }
                        );
                        this.projectsNames = res['projectNames'];
                        this.totalCount = res['totalCount'];
                        this.totalPages = Math.ceil(this.totalCount / this.pageParams.pageSize);
                        this.pagesArray =  Array(this.totalPages).fill(1).map((x, i) => ++i);
                    },
                    error => console.log(error)
                );
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
        for (const product of this.products) {
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
        if (action === 'NEW') {
            selectedStatus = Statuses.NEW;
        } else if (action === 'IN_PROGRESS') {
            selectedStatus = Statuses.IN_PROGRESS;
        } else if (action === 'FINISHED') {
            selectedStatus = Statuses.FINISHED;
        } else if (action === 'DELETE') {
            selectedStatus = null;
        } else {
            return;
        }

        if (selectedStatus == null) {
            this.windowPopService.setTitle('Delete Product');
            this.windowPopService.setContext('Are you sure?');
            this.windowPopService.setDetails('These products will be deleted permanently.');
            this.windowPopService.setDeleteProduct(true);
            this.windowPopService.activate();
            this.deleteProductsSubscription = this.productService.deleteProductConfirmed
                .subscribe( res => {
                    this.deleteProductsSubscription.unsubscribe();
                    this.changeStatus(selectedStatus)
                        .subscribe(
                            dataArray => {
                                this.products = new Array();
                                this.isMasterChecked = false;
                                this.activityService.addActivity(Actions.DELETED_PRODUCT).subscribe();
                                this.notificationService.showNotification();
                                this.ngOnInit();
                                // IN_PROGRESS Products
                                this.productService.getProductsCountByStatusId(Statuses.IN_PROGRESS)
                                .subscribe(
                                    products => {
                                        this.productService.inProgressProductsCount.next(products);
                                    },
                                    error => { console.log(error); }
                                );
                            },
                            error => console.log(error)
                        );
                },
                error => console.log(error)
            );
        } else { // change status
            this.changeStatus(selectedStatus)
                .subscribe(
                    dataArray => {
                        this.products = new Array();
                        this.isMasterChecked = false;
                        this.activityService.addActivity(Actions.UPDATED_PRODUCT).subscribe();
                        this.notificationService.showNotification();
                        this.ngOnInit();
                        // IN_PROGRESS Products
                        this.productService.getProductsCountByStatusId(Statuses.IN_PROGRESS)
                        .subscribe(
                            products => {
                                this.productService.inProgressProductsCount.next(products);
                            },
                            error => { console.log(error); }
                        );
                    },
                    error => console.log(error)
                );
        }
    }

    changeStatus(selectedStatus: number) {
        const observables: Observable<any>[] = new Array();

        this.products.forEach(
            (product) => {
                if ( product.isChecked) {
                    // delete
                    if (selectedStatus == null) {
                        observables.push(this.productService.deleteProduct(product.id));
                    } else { // change status
                        product.status.id = selectedStatus;
                        observables.push(this.productService.updateProductStatus(product));
                    }
                }
            }
        );

        return forkJoin(observables);
    }

    sortByDate() {
        this.sortByDateDesc = !this.sortByDateDesc;
        this.products = [];
        this.pageParams.field = 'date';
        if (this.sortByDateDesc) {
            this.pageParams.direction = 'desc';
        } else {
            this.pageParams.direction = 'asc';
        }
        this.ngOnInit();
    }

    sortByQuantity() {
        this.sortByQuantityDesc = !this.sortByQuantityDesc;
        this.products = [];
        this.pageParams.field = 'quantity';
        if (this.sortByQuantityDesc) {
            this.pageParams.direction = 'desc';
        } else {
            this.pageParams.direction = 'asc';
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

        if (projectName.match('null')) {
            this.filterParams.projectName = '';
        } else {
            this.filterParams.projectName = projectName;
            console.log(this.filterParams.projectName)
        }

        if (statusId >= 1 && statusId <= 3) {
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
            .then(() =>
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
                    });
                    this.carouselService.pictures = this.pictures;
                    this.carouselService.activate = true;
                },
                error => console.log(error)
            );
    }

    ngOnDestroy() {
        if (this.deleteProductsSubscription) {
            this.deleteProductsSubscription.unsubscribe();
        }
    }

    get pageParams() { return this.productService.pageParams; }
    get filterParams() { return this.productService.filterParams; }
}
