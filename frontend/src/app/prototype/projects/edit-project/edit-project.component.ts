import { Component, OnInit } from '@angular/core';
import { ActivityService } from 'src/app/general/home/activity/activity.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css']
})
export class EditProjectComponent implements OnInit {

  projectForm = new FormGroup({
    projectName: new FormControl(null, [Validators.required, Validators.minLength(2)]),
    companyName: new FormControl(null, [Validators.required, Validators.minLength(2)]),
    projectManager: new FormControl(null, [Validators.required, Validators.minLength(2)])
  })

  constructor(private activityService: ActivityService) { }

  ngOnInit() {
  }

  onAddSave() {
    // this.activityService.addActivity('2').subscribe();
  }

  onCancel() {
    this.projectForm.reset();
  }

  get projectName() {return this.projectForm.get('projectName')}
  get companyName() {return this.projectForm.get('companyName')}
  get projectManager() {return this.projectForm.get('projectManager')}
}
