import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user.model';
import { Router } from '@angular/router';

@Injectable()
export class AuthService {
  forgotPassFormSubmited = false;
  forgotPassFormError = false;
  forgottenPass = false;
  currentUser: User;
  rememberMe = false;
  
  constructor(private httpClient: HttpClient,
              private router: Router) {}

  getCsrf() {
    return this.httpClient.get<User>('api/users/current', {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
      observe: 'response'
    })
    .subscribe(
      resp => { 
        this.currentUser = resp.body;
        console.log(this.currentUser)
        if (this.currentUser) {
          this.router.navigate(['/']);
        } 
      },
      error => { if (error.status !== 401) console.log(error) }
    );
  }

  getCurrentUser() {
    return this.httpClient.get<User>('api/users/current', {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
      observe: 'response'
    })
    .subscribe(
      resp => { 
        this.currentUser = resp.body;
        console.log(this.currentUser)
        if (this.currentUser) {
          this.router.navigate(['/']);
        } 
      },
      error => { if (error.status !== 401) console.log(error) }
    );
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
      .subscribe(
        res => { 
          this.router.navigate(['/']);
        },
        error => {
          if (error.status === 200) {
            this.getCurrentUser();
            this.router.navigate(['/']);
          }
        }
    );
  }

  sentNewUserPassword(email: string) {
    return this.httpClient.post<any>('api/users/new-password', {},
      {
        headers: new HttpHeaders().set('Content-Type', 'application/json'),
        params: new HttpParams().set('email', email),
        observe: 'response'
      })
      .subscribe(
        res => { 
          this.forgotPassFormSubmited = true;
          setTimeout(() => {
            this.forgottenPass=false;
            this.forgotPassFormSubmited = false;
          }, 1200);
        },
        error => {
          console.log(error)
          this.forgotPassFormError = true;
        }
      );
  }

  isAuthenticated() {
    return this.currentUser !== undefined
  }

  logoutUser() {
    return this.httpClient.post<any>('api/logout',{}, 
      {observe: 'response'})
        .subscribe(
          res => {
            console.log(res);
            this.router.navigate(['/login']);
          },
          error => {
            if (error.status === 404) {
              this.currentUser = undefined;
              this.router.navigate(['/login']);
            }
          }
        )
  }

}