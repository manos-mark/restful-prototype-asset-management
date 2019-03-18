import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Search } from './search.model';

@Injectable()
export class SearchService {

    constructor(private httpClient: HttpClient) {}

    search(text: string) {
        return this.httpClient.get<Search>('api/search/' + text,
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
    }
}