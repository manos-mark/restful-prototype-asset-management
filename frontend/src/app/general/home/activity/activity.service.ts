import { Activity } from './activity.model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class ActivityService {
    activities:Activity;

    constructor(private httpClient: HttpClient) {}

    getActivities() {
        return this.httpClient.get<Activity>('api/activities', {
          headers: new HttpHeaders().set('Content-Type', 'application/json'),
          observe: 'response'
        })
        .subscribe(
          activity => { 
            this.activities = activity.body;
            console.log(this.activities);
          },
          error => { console.log(error) }
        );
      }
}