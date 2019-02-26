import { Component, OnInit, OnDestroy } from '@angular/core';
import { Project } from '../project.model';
import { ProjectsService } from '../projects.service';
import { Router } from '@angular/router';
import { Statuses } from '../../status.enum'

@Component({
  selector: 'app-list-projects',
  templateUrl: './list-projects.component.html',
  styleUrls: ['./list-projects.component.css']
})
export class ListProjectsComponent implements OnInit {
    field = 'date';
    page = 1;
    pageSize = 5;
    direction = 'asc';
    sortByDateAsc = true;
    sortByProductsCountAsc = true;
    projects: Project[] = [];
    totalCount: number;
    isMasterChecked: boolean;
    selectedProjectsCount = 0;

    constructor(private projectService: ProjectsService,
                private router: Router) {}

    ngOnInit() {
        this.projectService.getProjectsWithoutStatus(this.field, this.page, this.pageSize, this.direction)
            .subscribe(
                res => {
                    res['items'].map(
                        item => { this.projects.push(new Project(item)) }
                    )
                    this.totalCount = res['totalCount']
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
        let selectedStatus;
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
                    this.projectService.deleteProject(project.id).subscribe()
                }
            }
            return;
        } 
        else {
            return;
        }

        for (let project of this.projects) {
            if ( project.isChecked && (project.status.id !== selectedStatus) ) {
                this.projects = new Array();
                project.status.id = selectedStatus;
                this.projectService.updateProject(project)
                    .subscribe(
                        res => {
                            this.ngOnInit();
                        },
                        error => console.log(error)
                    )
            }
        }
    }

    sortByDate() {
        this.sortByDateAsc = !this.sortByDateAsc;

    }
    sortByProductsCount() {
        this.sortByProductsCountAsc = !this.sortByProductsCountAsc;

    }
}
