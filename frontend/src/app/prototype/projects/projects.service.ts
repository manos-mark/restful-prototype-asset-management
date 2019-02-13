import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class ProjectsService {

    constructor(private httpClient: HttpClient) {}

    getProjectsCountByStatusId(id: number) {
        return this.httpClient.post<number>('api/projects/count', {
            statusId: id
        }, 
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
        
    }

}