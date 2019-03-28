import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { ProductsService } from '../prototype/products/products.service';
import { ProjectsService } from '../prototype/projects/projects.service';
import { WindowPopService } from '../shared/window-pop/window-pop.service';
import { Statuses } from '../prototype/status.enum';
import { Subscription } from 'rxjs';
import { User } from '../auth/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {
    inProgressProductsCount: number;
    productsCountSubs: Subscription = null;
    newProjectsCount: number;
    projectsCountSubs: Subscription = null;
    private currentUser: User;

    constructor(private authService: AuthService,
                private productsService: ProductsService,
                private projectsService: ProjectsService,
                private windowPopService: WindowPopService) { }

    ngOnInit() {
        this.authService.getCurrentUserSubject()
            .subscribe(
                res => {
                    this.currentUser = res;
                    if (res) {
                        // IN_PROGRESS Products
                        this.productsService.getProductsCountByStatusId(Statuses.IN_PROGRESS)
                            .subscribe(
                                products => {
                                    this.productsService.inProgressProductsCount.next(products);
                                },
                                error => { console.log(error); }
                            );
                
                        // NEW Projects
                        this.projectsService.getProjectsCountByStatusId(Statuses.NEW)
                            .subscribe(
                            projects => {
                                this.projectsService.newProjectsCount.next(projects);
                            },
                            error => { console.log(error); }
                            );
                
                        this.productsCountSubs = this.productsService.inProgressProductsCount
                            .subscribe(
                                res => {
                                    this.inProgressProductsCount = res;
                                },
                                error => console.log(error)
                            );
                
                        this.projectsCountSubs = this.projectsService.newProjectsCount
                            .subscribe(
                                res => {
                                    this.newProjectsCount = res;
                                },
                                error => console.log(error)
                            );
                        }
                    }
        )
    }

    onLogout() {
        this.windowPopService.setTitle('Log out');
        this.windowPopService.setContext('Are you sure?');
        this.windowPopService.setDetails('You will be logged out!');
        this.windowPopService.activate();
        this.windowPopService.setLogout(true);
    }

    ngOnDestroy() {
        if (this.productsCountSubs) {
            this.productsCountSubs.unsubscribe();
        }
        if (this.projectsCountSubs) {
            this.projectsCountSubs.unsubscribe();
        }
    }

    // get currentUser() { return this.authService.getCurrentUser(); }
}
