import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators'
import { User } from './user.model';
@Injectable()
export class AuthService {
  
  constructor(private httpClient: HttpClient) {}

  getCurrentUser() {
    return this.httpClient.get<User>('api/users/current', {
      // headers: new HttpHeaders().set('Content-Type', 'application/json'),
      observe: 'response'
    })
  }
  
  loginUser(email: string, password: string, rememberMe: boolean) {
    return this.httpClient.post<any>('api/login', {},
      {
        // headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded'),
        params: new HttpParams()
                    .set('email', email)
                    .set('password', password) 
                    .set('remember-me', String(rememberMe)),
        observe: 'response'
      })
  }

  sentNewUserPassword(email: string) {
    return this.httpClient.post<any>('api/users/newPassword', {},
      {
        // headers: new HttpHeaders().set('Content-Type', 'application/json'),
        params: new HttpParams().set('email', email),
        observe: 'response'
      })
  }

  logoutUser() {
    return this.httpClient.post<any>('api/logout',{})
  }

}