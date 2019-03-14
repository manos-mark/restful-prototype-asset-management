import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user.model';
import { Router } from '@angular/router';
import { ActivityService } from '../general/home/activity/activity.service';

@Injectable()
export class AuthService {
  currentUser: User;
  rememberMe = false;
  
  constructor(private httpClient: HttpClient,
              private router: Router,
              private activityService: ActivityService) {}

  getCsrf() {
    return this.httpClient.get<User>('api/users/current', {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
      observe: 'response'
    })
  }

  getCurrentUser() {
    return this.httpClient.get<User>('api/users/current', {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
      observe: 'response'
    })
  }
  
  loginUser(email: string, password: string) {
    return this.httpClient.post<any>('api/login', {},
      {
        headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded'),
        params: new HttpParams()
                    .set('email', email)
                    .set('password', password) 
                    .set('remember-me', String(this.rememberMe)),
        observe: 'response'
      })
  }

  sentNewUserPassword(email: string) {
    return this.httpClient.post<any>('api/users/new-password', {},
      {
        headers: new HttpHeaders().set('Content-Type', 'application/json'),
        params: new HttpParams().set('email', email),
        observe: 'response'
      })
  }

  updateUser(oldPassword:number, newPassword: number, repeatNewPassword: number) {
    return this.httpClient.put<any>('api/users/' + this.currentUser.id.toString() + "/new-password", 
      {
        oldPassword: oldPassword,
        password: newPassword,
        matchingPassword: repeatNewPassword
      },
      {
        headers: new HttpHeaders().set('Content-Type', 'application/json'),
        observe: 'response'
      }
    );
  }

  acceptCookies() {
    return this.httpClient.put<any>('api/users/' + this.currentUser.id.toString(),  this.currentUser,
        {
        headers: new HttpHeaders().set('Content-Type', 'application/json'),
        observe: 'response'
        }
    );
  }

  isAuthenticated() {
    return this.currentUser !== undefined && this.currentUser !== null
  }

  logout() {
    return this.httpClient.post<any>('api/logout',{}, 
      {observe: 'response'})
  }

}