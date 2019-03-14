import { Component, OnInit } from '@angular/core';
import { Project } from '../project.model';
import { ProjectsService } from '../projects.service';
import { Router } from '@angular/router';
import { Statuses } from '../../status.enum'
import { Observable, forkJoin } from 'rxjs';
import { FilterParams } from '../filter-params.model';
import { PageParams } from '../page-params.model';
import { Actions } from 'src/app/general/home/activity/action.enum';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { BreadcrumbsService } from 'src/app/shared/breadcrumbs.service';


@Component({
  selector: 'app-list-projects',
  templateUrl: './list-projects.component.html',
  styleUrls: ['./list-projects.component.css']
})
export class ListProjectsComponent implements OnInit {
    filterParams = new FilterParams;
    pageParams = new PageParams;
    sortByDateAsc = true;
    sortByProductsCountAsc = true;
    isMasterChecked: boolean = false;
    projects: Project[] = [];
    totalCount: number;
    selectedProjectsCount = 0;
    pagesArray = [];
    totalPages = this.projects.length / this.pageParams.pageSize;

    constructor(private projectService: ProjectsService,
                private router: Router,
                private activityService: ActivityService,
                private breadcrumbsService: BreadcrumbsService) {

        this.breadcrumbsService.breadcrumbs = [];
        this.breadcrumbsService.breadcrumbs.push({
            name: "Prototype > Projects",
            src: "prototype/projects"
        });
        this.isMasterChecked = false;
        this.projectService.getProjects(this.pageParams, this.filterParams)
            .subscribe(
                res => {
                    res['items'].map(
                        item => { this.projects.push(new Project(item)) }
                    )
                    this.totalCount = res['totalCount'];
                    this.totalPages = Math.ceil(this.totalCount / this.pageParams.pageSize);
                    this.pagesArray =  Array(this.totalPages).fill(1).map((x,i)=>++i);
                },
                error => console.log(error)
            )
    }

    ngOnInit() {
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
                    this.projects = new Array();
                    this.isMasterChecked = false;
                    this.ngOnInit();
                },
                error => console.log(error)
            );
    }

    changeStatus(selectedStatus: number) {
        let observables: Observable<any>[] = new Array();
        
        this.projects.forEach(
            (project) => {
                if ( project.isChecked) {
                    // delete
                    if (selectedStatus == null) {
                        observables.push(this.projectService.deleteProject(project.id));
                        observables.push(this.activityService.addActivity(Actions.DELETED_PROJECT));
                    }
                    // change status
                    else {
                        project.status.id = selectedStatus;
                        observables.push(this.projectService.updateProject(project));
                        observables.push(this.activityService.addActivity(Actions.UPDATED_PROJECT));
                    }
                }
            }
        )

        return forkJoin(observables);
    }

    sortByDate() {
        this.sortByDateAsc = !this.sortByDateAsc;
        this.projects = [];
        this.pageParams.field = 'date';
        if (this.sortByDateAsc) {
            this.pageParams.direction = 'asc';
        } else {
            this.pageParams.direction = 'desc';
        }
        this.ngOnInit();
    }

    sortByProductsCount() {
        this.sortByProductsCountAsc = !this.sortByProductsCountAsc;
        this.projects = [];
        this.pageParams.field = 'products';
        if (this.sortByProductsCountAsc) {
            this.pageParams.direction = 'asc';
        } else {
            this.pageParams.direction = 'desc';
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

        if(statusId >= 1 && statusId <=3) {
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
        this.router.navigateByUrl('/', {skipLocationChange: true})
            .then(()=>
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
}
