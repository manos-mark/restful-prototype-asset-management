import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Project } from './project.model';

@Injectable()
export class ProjectsService {
    editMode = false;

    constructor(private httpClient: HttpClient) {}

    getProjectManagers() {
        return this.httpClient.get<any>('api/projects/project-managers',
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }

    getProjectsCountByStatusId(id: number) {
        return this.httpClient.post<number>('api/projects/count', {
            statusId: id
        }, 
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

    getAllProjects() {
        return this.httpClient.get<Project[]>('api/projects/all',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }

    addProject(projectName: string, companyName: string, projectManagerId: number) {
        let currDate = (new Date).toLocaleDateString('en-GB');
        return this.httpClient.post<Project>('api/projects',
        {
            projectName: projectName,
            companyName: companyName,
            projectManagerId: projectManagerId,
            date: currDate,
            statusId: 2
        },
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }

    updateProject(projectName: string, companyName: string, 
        projectManagerId: number, statusId: number, projectId: number) {
        return this.httpClient.put<Project>('api/projects/' + projectId,
        {
            projectName: projectName,
            companyName: companyName,
            projectManagerId: projectManagerId,
            statusId: statusId
        },
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }


}