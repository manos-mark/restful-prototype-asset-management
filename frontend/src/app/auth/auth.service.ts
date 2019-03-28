import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user.model';
import { Router } from '@angular/router';
import { ActivityService } from '../general/home/activity/activity.service';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable()
export class AuthService {
    private currentUser: BehaviorSubject<User> = new BehaviorSubject<User>(null);
    private rememberMe = false;
    isForgottenPassActive = false;

    constructor(private httpClient: HttpClient,
                private router: Router,
                private activityService: ActivityService) {}

    retrieveCurrentUser() {
        return this.httpClient.get<any>('api/users/current', {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'response'
        });
    }

    getCurrentUser() {
        return this.currentUser.value;
    }

    getCurrentUserSubject() {
        return this.currentUser;
    }

    setCurrentUser(user: User) {
        this.currentUser.next(user);
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
        });
    }

    resetPassword(email: string) {
        return this.httpClient.post<any>('api/users/reset-password', {
            email: email
        },
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'response'
        });
    }

    updateUser(oldPassword: number, newPassword: number, repeatNewPassword: number) {
        return this.httpClient.put<any>('api/users/new-password',
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
        return this.httpClient.put<any>('api/users/',  this.currentUser.value,
            {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'response'
            }
        );
    }

    isAuthenticated() {
        const promise = new Promise(
            (resolve, reject) => {
                if (this.currentUser.value !== undefined && this.currentUser.value !== null) {
                    resolve(true);
                } else {
                    resolve(
                        this.retrieveCurrentUser().toPromise().then(
                            res => {
                                if (res.body) {
                                    this.setCurrentUser(res.body);
                                    resolve(true);
                                } else {
                                    resolve(false);
                                }
                            }
                        )
                    );
                }
            }
        );
        return promise;
    }

    logout() {
        return this.httpClient.post<any>('api/logout', {},
        {observe: 'response'});
    }

}
