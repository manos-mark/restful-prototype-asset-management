import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Project } from './project.model';
import { Product } from '../products/product.model';
import { FilterParams } from './filter-params.model';
import { PageParams } from './page-params.model';
import { toHttpParams } from 'src/app/shared/http-params-converter';
import { Statuses } from '../status.enum';
import { Subject } from 'rxjs';

@Injectable()
export class ProjectsService {
    public newProjectsCount = new Subject<number>();
    public deleteProjectConfirmed = new Subject<boolean>();

    private _editMode : boolean;
    public get editMode() : boolean {
        return this._editMode;
    }
    public set editMode(v : boolean) {
        this._editMode = v;
    }

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

    getProjects(pageParams: PageParams,filterParams: FilterParams) {

        const params = {
            field: pageParams.field,
            page: pageParams.page,
            pageSize: pageParams.pageSize,
            direction: pageParams.direction,
            fromDate: filterParams.fromDate,
            toDate: filterParams.toDate,
            statusId: filterParams.statusId
        }

        return this.httpClient.get<Project>('api/projects/',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body',
            params: toHttpParams(params)
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
        return this.httpClient.post<Project>('api/projects',
        {
            projectName: projectName,
            companyName: companyName,
            projectManagerId: projectManagerId,
            statusId: Statuses.NEW
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