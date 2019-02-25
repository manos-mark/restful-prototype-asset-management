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
export class ListProjectsComponent implements OnInit, OnDestroy {
    projects: Project[] = [];
    totalCount: number;
    isMasterChecked: boolean;

    constructor(private projectService: ProjectsService,
                private router: Router) {}

    ngOnInit() {
        this.projectService.getProjectsWithoutStatus('date',1,5,'asc')
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
        this.router.navigate(['/prototype/projects/', projectId, 'edit'], 
            {queryParams: { projectId: projectId }}
        );
    }

    onSelectAll() {
        for (let project of this.projects) {
            project.isChecked = !this.isMasterChecked;
        }
        this.isMasterChecked = !this.isMasterChecked;
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
                    this.projectService.deleteProject(project.id)
                        .subscribe(
                            res => {this.ngOnDestroy(); this.ngOnInit()},
                            error => console.log(error)
                        )
                }
            }
        } 
        else {
            return;
        }

        for (let project of this.projects) {
            if (project.isChecked) {
                project.status.id = selectedStatus;
                this.projectService.updateProject(project)
                    .subscribe(
                        res => {this.ngOnDestroy(); this.ngOnInit()},
                        error => console.log(error)
                    )
            }
        }
    }

    ngOnDestroy() {
        this.projects = [];
    }

}
