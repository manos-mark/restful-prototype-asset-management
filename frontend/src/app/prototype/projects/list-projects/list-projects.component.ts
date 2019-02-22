import { Component, OnInit } from '@angular/core';
import { Project } from '../project.model';
import { ProjectsService } from '../projects.service';

@Component({
  selector: 'app-list-projects',
  templateUrl: './list-projects.component.html',
  styleUrls: ['./list-projects.component.css']
})
export class ListProjectsComponent implements OnInit {
    projects: Project[];
    totalCount: number;

    constructor(private projectService: ProjectsService) { }

    ngOnInit() {
        this.projectService.getProjectsWithoutStatus('date',1,5,'asc')
            .subscribe(
                res => {this.projects = res['items']; this.totalCount = res['totalCount']},
                error => console.log(error)
            )
    }

}
