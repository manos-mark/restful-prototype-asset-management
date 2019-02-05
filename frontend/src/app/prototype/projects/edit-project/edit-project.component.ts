import { Component, OnInit } from '@angular/core';
import { ActivityService } from 'src/app/general/home/activity/activity.service';

@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.component.html',
  styleUrls: ['./edit-project.component.css']
})
export class EditProjectComponent implements OnInit {

  constructor(private activityService: ActivityService) { }

  ngOnInit() {
  }

  onAddSave() {
    this.activityService.addActivity('2').subscribe();
  }
}
