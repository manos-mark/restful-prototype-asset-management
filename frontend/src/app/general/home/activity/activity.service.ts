import { Activity } from './activity.model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class ActivityService {

  constructor(private httpClient: HttpClient) {}

    getActivities() {
      return this.httpClient.get<Array<Activity>>('api/activities', {
        headers: new HttpHeaders().set('Content-Type', 'application/json'),
        observe: 'body'
      });

    }

    addActivity(actionId: number) {
      return this.httpClient.post<any>('api/activities', {
        actionId: actionId
      }, {
        headers: new HttpHeaders().set('Content-Type', 'application/json'),
        observe: 'response'
      });
    }
}
