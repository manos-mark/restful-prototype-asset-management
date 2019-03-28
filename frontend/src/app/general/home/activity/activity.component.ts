import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { ActivityService } from './activity.service';
import { Activity } from './activity.model';


@Component({
  selector: 'app-activity',
  templateUrl: './activity.component.html',
  styleUrls: ['./activity.component.css']
})
export class ActivityComponent implements OnInit {
    activities: Array<Activity> = [];

    constructor(public authService: AuthService,
                public actService: ActivityService) { }

    ngOnInit() {
        this.actService.getActivities()
        .subscribe(
            activities => {
            activities.map(
                activity => { this.activities.push(new Activity(activity)); }
            );
            },
            error => { console.log(error); }
        );
    }

    get currentUser() { return this.authService.getCurrentUser() }
}
