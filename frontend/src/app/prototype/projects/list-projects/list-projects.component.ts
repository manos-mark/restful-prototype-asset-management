import { Component, OnInit, OnDestroy } from '@angular/core';
import { Project } from '../project.model';
import { ProjectsService } from '../projects.service';
import { Router } from '@angular/router';
import { Statuses } from '../../status.enum';
import { Observable, forkJoin, Subscription } from 'rxjs';
import { FilterParams } from '../filter-params.model';
import { PageParams } from '../page-params.model';
import { Actions } from 'src/app/general/home/activity/action.enum';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { BreadcrumbsService } from 'src/app/shared/breadcrumbs.service';
import { NotificationService } from 'src/app/shared/notification/notification.service';
import { SearchService } from 'src/app/header/search/search.service';
import { WindowPopService } from 'src/app/shared/window-pop/window-pop.service';
import { LoaderService } from 'src/app/shared/loader/loader.service';


@Component({
  selector: 'app-list-projects',
  templateUrl: './list-projects.component.html',
  styleUrls: ['./list-projects.component.css']
})
export class ListProjectsComponent implements OnInit, OnDestroy {
    sortByDateDesc = true;
    sortedByDate = true;
    sortByProductsCountDesc = false;
    isChangeStatusSelected = false;
    isFilterChanged = false;
    isMasterChecked = false;
    projects: Project[] = [];
    totalCount: number;
    selectedProjectsCount = 0;
    pagesArray = [];
    totalPages = this.projects.length / this.pageParams.pageSize;
    deleteProjectsSubscription: Subscription = null;
    dateToFilter = null;
    dateFromFilter = null;

    constructor(private projectService: ProjectsService,
                private router: Router,
                private activityService: ActivityService,
                private breadcrumbsService: BreadcrumbsService,
                private notificationService: NotificationService,
                private searchService: SearchService,
                private windowPopService: WindowPopService,
                private loaderService: LoaderService) {
        this.breadcrumbsService.setBreadcrumbsProjects();
    }

    ngOnInit() {
        this.loaderService.show();
        this.isFilterChanged = false;
        this.isChangeStatusSelected = false;
        this.selectedProjectsCount = 0;
        this.searchService.clear();
        this.isMasterChecked = false;
        this.projectService.getProjects(this.pageParams, this.filterParams)
            .subscribe(
                res => {
                    res['items'].map(
                        item => { this.projects.push(new Project(item)); }
                    );
                    this.totalCount = res['totalCount'];
                    this.totalPages = Math.ceil(this.totalCount / this.pageParams.pageSize);
                    this.pagesArray =  Array(this.totalPages).fill(1).map((x, i) => ++i);
                    this.loaderService.hide();
                },
                error => console.log(error)
            );
    }

    onEdit(projectId: number) {
        this.projectService.editMode = true;
        this.router.navigate(['prototype/projects/', projectId, 'edit'],
            {queryParams: { projectId: projectId }}
        );
    }

    onSelect(project: Project) {
        if (project.isChecked) {
            --this.selectedProjectsCount;
        } else {
            ++this.selectedProjectsCount;
        }
        project.isChecked = !project.isChecked;
        this.isMasterChecked = false;
    }

    onSelectAll() {
        let projectsCount = 0;
        for (let project of this.projects) {
            project.isChecked = !this.isMasterChecked;
            projectsCount++;
        }
        this.isMasterChecked = !this.isMasterChecked;

        if (this.isMasterChecked) {
            this.selectedProjectsCount = projectsCount;
        } else {
            this.selectedProjectsCount = 0;
        }
    }

    onApplyChanges(action) {
        let selectedStatus: number = undefined;
        if (action === "NEW") {
            selectedStatus = Statuses.NEW;
        } 
        else if (action === "IN_PROGRESS") {
            selectedStatus = Statuses.IN_PROGRESS;
        } else if (action === "FINISHED") {
            selectedStatus = Statuses.FINISHED;
        } else if (action === "DELETE") {
            selectedStatus = null;
        } else {
            return;
        }

        if (selectedStatus == null) {
            this.deleteProjects(selectedStatus);
        } else { // change status
            this.changeStatus(selectedStatus);
        }
    }

    changeStatus(selectedStatus: number) {
        this.changeStatusObservalbes(selectedStatus)
            .subscribe(
                dataArray => {
                    this.projects = new Array();
                    this.isMasterChecked = false;
                    this.activityService.addActivity(Actions.UPDATED_PROJECT).subscribe();
                    this.notificationService.showNotification();
                    this.ngOnInit();
                    // NEW Projects
                    this.projectService.getProjectsCountByStatusId(Statuses.NEW)
                    .subscribe(
                        projects => {
                            this.projectService.newProjectsCount.next(projects);
                        },
                        error => { console.log(error); }
                        );
                },
                error => console.log(error)
            );
    }

    deleteProjects(selectedStatus: number) {
        this.windowPopService.setTitle('Delete Project');
            this.windowPopService.setContext('Are you sure?');
            this.windowPopService.setDetails('These projects will be deleted permanently.');
            this.windowPopService.setDeleteProject(true);
            this.windowPopService.activate();
            this.deleteProjectsSubscription = this.projectService.deleteProjectConfirmed
                .subscribe( res => {
                    this.deleteProjectsSubscription.unsubscribe();
                    this.changeStatusObservalbes(selectedStatus)
                        .subscribe(
                            dataArray => {
                                this.projects = new Array();
                                this.isMasterChecked = false;
                                this.activityService.addActivity(Actions.DELETED_PROJECT).subscribe();
                                this.notificationService.showNotification();
                                this.ngOnInit();
                                // NEW Projects
                                this.projectService.getProjectsCountByStatusId(Statuses.NEW)
                                .subscribe(
                                projects => {
                                    this.projectService.newProjectsCount.next(projects);
                                },
                                error => { console.log(error); }
                                );
                            },
                            error => console.log(error)
                        );
                },
                    error => console.log(error)
                );
    }

    changeStatusObservalbes(selectedStatus: number) {
        let observables: Observable<any>[] = new Array();

        this.projects.forEach(
            (project) => {
                if ( project.isChecked) {
                    // delete
                    if (selectedStatus == null) {
                        observables.push(this.projectService.deleteProject(project.id));
                    } else { // change status
                        observables.push(this.projectService.updateProject(project, selectedStatus));
                    }
                }
            }
        )

        return forkJoin(observables);
    }

    sortByDate() {
        this.sortedByDate = true;
        this.sortByDateDesc = !this.sortByDateDesc;
        this.projects = [];
        this.pageParams.field = 'date';
        if (this.sortByDateDesc) {
            this.pageParams.direction = 'desc';
        } else {
            this.pageParams.direction = 'asc';
        }
        this.ngOnInit();
    }

    sortByProductsCount() {
        this.sortedByDate = false;
        this.sortByProductsCountDesc = !this.sortByProductsCountDesc;
        this.projects = [];
        this.pageParams.field = 'products';
        if (this.sortByProductsCountDesc) {
            this.pageParams.direction = 'desc';
        } else {
            this.pageParams.direction = 'asc';
        }
        this.ngOnInit();
    }

    changeResultsPerPage(resultsPerPage: number) {
        this.projects = new Array();
        this.pageParams.pageSize = resultsPerPage;
        this.pageParams.page = 1;
        this.ngOnInit();
    }

    changePageUp() {
        if (this.pageParams.page < this.totalPages) {
            this.projects = new Array();
            this.pageParams.page ++;
            this.ngOnInit();
        }
    }

    changePageDown() {
        if (this.pageParams.page > 1) {
            this.projects = new Array();
            this.pageParams.page --;
            this.ngOnInit();
        }
    }

    changePage(target: number) {
        if (target >= 1 && target <= this.totalPages && target !== this.pageParams.page) {
            this.projects = new Array();
            this.pageParams.page = target;
            this.ngOnInit();
        }
    }

    applyFilters(statusId: number, dateFrom: Date, dateTo: Date) {
        this.filterParams.fromDate = dateFrom;
        this.filterParams.toDate = dateTo;
        if (statusId >= 1 && statusId <= 3) {
            this.projects = new Array();
            this.filterParams.statusId = statusId;
            this.ngOnInit();
        } else {
            this.projects = new Array();
            this.filterParams.statusId = null;
            this.ngOnInit();
        }
    }

    clearFilters() {
        this.filterParams.clear();
        this.router.navigateByUrl('/', {skipLocationChange: true})
            .then(() =>
                this.router.navigate(['prototype/projects/'])
            );
    }

    onOpenProducts(projectName: string) {
        this.router.navigate(['prototype/products/'],
            {queryParams: { projectName: projectName }}
        );
    }

    onAddNewProduct(projectId: number) {
        this.router.navigate(['prototype/products/new'],
            {queryParams: { projectId: projectId }}
        );
    }

    ngOnDestroy() {
        if (this.deleteProjectsSubscription) {
            this.deleteProjectsSubscription.unsubscribe();
        }
    }

    doNothing() {}

    public get pageParams() { return this.projectService.pageParams; }
    public get filterParams() { return this.projectService.filterParams; }
}
