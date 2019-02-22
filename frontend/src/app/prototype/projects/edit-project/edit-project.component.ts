import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { ProjectsService } from '../projects.service';
import { Project } from '../project.model';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css']
})
export class EditProjectComponent implements OnInit {
    projectManagers: {
        id: number;
        name: string;
    }[];
    projectId: number;

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
        this.projectForm.controls.statusId.setValue(2);
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
                    this.projectForm.controls.projectName.setValue(res.projectName);
                    this.projectForm.controls.companyName.setValue(res.companyName);
                    this.projectForm.controls.projectManagerId.setValue(res.projectManagerId);
                    this.projectForm.controls.statusId.enable();
                    this.projectForm.controls.statusId.setValue(res.statusId);
                    this.projectId = res.projectId;
                },
                error => console.log(error)
            )
            
        }
    }

    onAddSave() {
        // on edit mode update
        if (this.projectService.editMode) {
            this.projectService.updateProject(this.projectName.value, 
                this.companyName.value, this.projectManagerId.value, this.statusId.value, this.projectId)
                    .subscribe(
                        res => {
                            this.activityService.addActivity('1')
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
                            this.activityService.addActivity('2')
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
    }

    get projectName() {return this.projectForm.get('projectName')}
    get companyName() {return this.projectForm.get('companyName')}
    get projectManagerId() {return this.projectForm.get('projectManagerId')}
    get statusId() {return this.projectForm.get('statusId')}
}
