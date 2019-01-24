import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { map } from 'rxjs/operators'
import { User } from './user.model';
@Injectable()
export class AuthService {
  
  constructor(private httpClient: HttpClient) {}

  getCurrentUser() {
    return this.httpClient.get<User>('api/users/current', {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
      observe: 'response'
    });
  }
  
  loginUser(email: string, password: string) {
    return this.httpClient.post<any>('api/login', {},
      {
        headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded'),
        params: new HttpParams().set('email', email).set('password', password) 
      }
    )
    .pipe(
      map(res => {
        if (!res.response) {
          throw new Error('Value expected!');
        }
        return res.response;
      })
    )
  }

  logoutUser() {
    return this.httpClient.post<any>('api/logout',{})
  }

}