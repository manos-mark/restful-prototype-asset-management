import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { ActivityService } from './activity.service';
import { Activity } from './activity.model';
import { LoaderService } from 'src/app/shared/loader/loader.service';


@Component({
  selector: 'app-activity',
  templateUrl: './activity.component.html',
  styleUrls: ['./activity.component.css']
})
export class ActivityComponent implements OnInit {
    activities: Array<Activity> = [];

    constructor(public authService: AuthService,
                public actService: ActivityService,
                private loaderService: LoaderService) { }

    ngOnInit() {
        this.loaderService.show();
        this.actService.getActivities()
        .subscribe(
            activities => {
            activities.map(
                activity => { this.activities.push(new Activity(activity)); }
            );
            this.loaderService.hide();
            },
            error => { console.log(error); }
        );
    }

    get currentUser() { return this.authService.getCurrentUser() }
}
