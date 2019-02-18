import { Component, OnInit, Input } from '@angular/core';
import { Project } from '../../project.model';
import { Router } from '@angular/router';
import { ProjectsService } from '../../projects.service';

@Component({
  selector: 'app-project-item',
  templateUrl: './project-item.component.html',
  styleUrls: ['./project-item.component.css']
})
export class ProjectItemComponent implements OnInit {
    @Input() project: Project;
    productsCount = 0;

    constructor(private projectService: ProjectsService,
                private router: Router) { }

    ngOnInit() {
        this.projectService.getProductsCountByProjectId(this.project.id)
            .subscribe(
                res => this.productsCount = res,
                error => console.log(error)
            )
    }

    onEdit() {
        this.projectService.editMode = true;
        this.router.navigate(['prototype/projects/' + this.project.id + '/edit']);
    }
}
