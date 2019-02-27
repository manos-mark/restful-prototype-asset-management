import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Project } from './project.model';
import { Product } from '../products/product.model';

@Injectable()
export class ProjectsService {
    editMode = false;

    constructor(private httpClient: HttpClient) {}

    getProjectById(id: number) {
        return this.httpClient.get<Project>('api/projects/' + id,
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }

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

    getProjects(field: string, page: number, pageSize: number, direction: string,
        dateFromFilter: string, dateToFilter: string, statusFilter: number) {
        return this.httpClient.get<Project>('api/projects/',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body',
            params: new HttpParams()
                            .set('field', field) 
                            .set('page', String(page))
                            .set('pageSize', String(pageSize))
                            .set('direction', direction)
                            .set('dateFromFilter',dateFromFilter) 
                            .set('dateToFilter', dateToFilter) 
                            .set('statusId', String(statusFilter)) 
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
            observe: 'response'
        })
    }

    updateProject(project: any) {
        return this.httpClient.put<any>('api/projects/' + project.id,
        {
            projectName: project.projectName,
            companyName: project.companyName,
            projectManagerId: project.projectManager.id,
            statusId: project.status.id
        },
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'response'
        })
    }

    getProductsByProjectId(projectId: number) {
        return this.httpClient.get<Product[]>('api/projects/' + projectId + '/products',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }

    deleteProject(projectId: number) {
        return this.httpClient.delete('api/projects/' + projectId,  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'response'
        })
    }
}