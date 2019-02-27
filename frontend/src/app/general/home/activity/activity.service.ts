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
      })
      
    }
        
    addActivity(actionId: string) {
      let currDate = (new Date).toLocaleString('en-GB');
      return this.httpClient.post<any>('api/activities', {
        date: currDate,
        actionId: actionId
      }, {
        headers: new HttpHeaders().set('Content-Type', 'application/json'),
        observe: 'response'
      })
    }    
}