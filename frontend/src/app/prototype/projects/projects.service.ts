import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Project } from './project.model';

@Injectable()
export class ProjectsService {
    editMode = false;

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

    getProductsCountByProjectId(id: number) {
        return this.httpClient.get<number>('api/projects/' + id + '/products/count', 
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
        
    }

    getProjects(statusId: number, field: string, page: number, pageSize: number, direction: string) {
        return this.httpClient.get<Project>('api/projects/',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body',
            params: new HttpParams()
                            .set('statusId', String(statusId))
                            .set('field', field) 
                            .set('page', String(page))
                            .set('pageSize', String(pageSize))
                            .set('direction', direction) 
        })
    }

    getProjectsWithoutParams() {
        return this.httpClient.get<Project>('api/projects/',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }

    getProjectName(projectId: number) {
        return this.httpClient.get<Project>('api/projects/' + projectId + '/name',
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }
}