import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { ProjectsService } from '../projects.service';
import { Project } from '../project.model';
import { Product } from '../../products/product.model';
import { Statuses, StatusesMap } from '../../status.enum';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css']
})
export class EditProjectComponent implements OnInit, OnDestroy {
    projectManagers: {
        id: number;
        name: string;
    }[];
    project: Project;
    products: Product[] = [];

    projectForm = new FormGroup({
        projectName: new FormControl(null, [Validators.required, Validators.minLength(2)]),
        companyName: new FormControl(null, [Validators.required, Validators.minLength(2)]),
        projectManagerId: new FormControl("Choose project manager", [Validators.required, Validators.pattern("^[0-9]+$")]),
        statusId: new FormControl(null, [Validators.required, Validators.pattern("^[0-9]+$")])
    })

    constructor(private activityService: ActivityService,
                private projectService: ProjectsService,
                private router: Router,
                private route: ActivatedRoute) { }

    ngOnInit() {
        // when add new project status always will be new and disabled
        this.projectForm.controls.statusId.setValue(Statuses.NEW);
        this.projectForm.controls.statusId.disable();
        // get project managers for the dropdown
        this.projectService.getProjectManagers()
                .subscribe(
                    res => this.projectManagers = res,
                    error => console.log(error)
                )
        // on edit mode init the fields        
        if (this.projectService.editMode) {
            this.route.queryParams.subscribe(
                res => {
                    this.projectService.getProjectById(res.projectId).subscribe(
                        res => {
                            this.project = new Project(res);
                            this.projectForm.controls.projectName.setValue(this.project.projectName);
                            this.projectForm.controls.companyName.setValue(this.project.companyName);
                            this.projectForm.controls.projectManagerId.setValue(this.project.projectManager.id);
                            this.projectForm.controls.statusId.enable();
                            this.projectForm.controls.statusId.setValue(this.project.status.id);
                            this.projectService.getProductsByProjectId(this.project.id)
                                .subscribe(
                                    res => {this.products = res; console.log(this.products)},
                                    error => console.log(error)
                                )
                        },
                        error => console.log(error)
                    )
                },
                error => console.log(error)
            )
        }
    }

    onAddSave() {
        // on edit mode update
        if (this.projectService.editMode) {
            let tempProject = new Object({
                projectName: this.projectName.value,
                companyName: this.companyName.value,
                projectManager: { id: this.projectManagerId.value },
                status: { id: this.statusId.value },
                id: this.project.id,
            });
            console.log(tempProject)
            this.projectService.updateProject(tempProject)
                    .subscribe(
                        res => {
                            this.activityService.addActivity(Statuses.IN_PROGRESS.toString())
                                .subscribe(
                                    res => this.router.navigate(['/prototype/projects']),
                                    error => console.log(error)
                                )
                        },
                        error => console.log(error)
                    )
        }
        // else add new project
        else {
            this.projectService.addProject(this.projectName.value, 
                this.companyName.value, this.projectManagerId.value)
                    .subscribe(
                        res => {
                            this.activityService.addActivity(Statuses.NEW.toString())
                                .subscribe(
                                    res => this.router.navigate(['/prototype/projects']),
                                    error => console.log(error)
                                )
                        },
                        error => console.log(error)
                    )
        }
    }

    onCancel() {
        this.projectForm.reset();
        this.router.navigate(['prototype','projects']);
    }

    ngOnDestroy() {
        this.projectService.editMode = false;
    }

    get projectName() {return this.projectForm.get('projectName')}
    get companyName() {return this.projectForm.get('companyName')}
    get projectManagerId() {return this.projectForm.get('projectManagerId')}
    get statusId() {return this.projectForm.get('statusId')}
    get editMode() {return this.projectService.editMode}
}
