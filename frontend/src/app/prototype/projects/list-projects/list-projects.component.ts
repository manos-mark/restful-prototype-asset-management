import { Component, OnInit, OnDestroy } from '@angular/core';
import { Project } from '../project.model';
import { ProjectsService } from '../projects.service';
import { Router } from '@angular/router';
import { Statuses } from '../../status.enum'
import { Observable, forkJoin } from 'rxjs';
import { FilterParams } from '../filter-params.model';
import { PageParams } from '../page-params.model';


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
    projects: Project[] = [];
    totalCount: number;
    isMasterChecked: boolean;
    selectedProjectsCount = 0;
    pagesArray = [];
    totalPages = this.projects.length / this.pageParams.pageSize;

    constructor(private projectService: ProjectsService,
                private router: Router) {}

    ngOnInit() {
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
            for (let project of this.projects) {
                if (project.isChecked) {
                    this.projectService.deleteProject(project.id).subscribe(
                        res => {
                            this.router.navigateByUrl('/', {skipLocationChange: true})
                                .then(()=>
                                    this.router.navigate(['prototype/projects/'])
                                );
                        }
                    )
                }
            }
            return;
        } 
        else {
            return;
        }

        this.changeStatus(selectedStatus)
            .subscribe(
                dataArray => {
                    this.router.navigateByUrl('/', {skipLocationChange: true})
                        .then(()=>
                            this.router.navigate(['prototype/projects/'])
                        );
                },
                error => console.log(error)
            );
    }

    changeStatus(selectedStatus: number) {
        let observables: Observable<any>[] = new Array();
        
        this.projects.forEach(
            (project) => {
                if ( project.isChecked) {
                    project.status.id = selectedStatus;
                    observables.push(this.projectService.updateProject(project));
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
        
        this.filterParams.fromDate = dateFrom;//.toLocaleString('en-GB');
        this.filterParams.toDate = dateTo;//.toLocaleString('en-GB');

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

}
