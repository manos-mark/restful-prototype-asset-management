import { Component, OnInit } from '@angular/core';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ProjectsService } from '../projects.service';
import { Router } from '@angular/router';

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

    projectForm = new FormGroup({
        projectName: new FormControl(null, [Validators.required, Validators.minLength(2)]),
        companyName: new FormControl(null, [Validators.required, Validators.minLength(2)]),
        projectManagerId: new FormControl("Choose project manager", [Validators.required, Validators.pattern("^[0-9]+$")])
    })

    constructor(private activityService: ActivityService,
                private projectService: ProjectsService,
                private router: Router) { }

    ngOnInit() {
        this.projectService.getProjectManagers()
            .subscribe(
                res => this.projectManagers = res,
                error => console.log(error)
            )
    }

    onAddSave() {
        this.projectService.addProject(this.projectName.value, 
            this.companyName.value, this.projectManagerId.value)
                .subscribe(
                    res => {
                        console.log(res);this.activityService.addActivity('2')
                            .subscribe(
                                res => this.router.navigate(['/prototype/projects']),
                                error => console.log(error)
                            )
                    },
                    error => console.log(error)
                )
    }

    onCancel() {
        this.projectForm.reset();
    }

    get projectName() {return this.projectForm.get('projectName')}
    get companyName() {return this.projectForm.get('companyName')}
    get projectManagerId() {return this.projectForm.get('projectManagerId')}
}
