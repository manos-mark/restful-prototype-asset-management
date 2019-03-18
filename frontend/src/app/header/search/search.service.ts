import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Search } from './search.model';
import { SearchProduct } from './search-product.model';
import { SearchProject } from './search-project.model';

@Injectable()
export class SearchService {
    products: SearchProduct[] = [];
    projects: SearchProject[] = [];

    constructor(private httpClient: HttpClient) {}

    search(text: string) {
        return this.httpClient.get<Search>('api/search/' + text,
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        });
    }

    clear() {
        this.products = [];
        this.projects = [];
    }
}
