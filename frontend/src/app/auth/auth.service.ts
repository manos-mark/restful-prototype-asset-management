import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class AuthService {
  
  constructor(private httpClient: HttpClient) {}
  
  loginUser(email: string, password: string) {
    // let headers = new Headers();
    // headers.append('Content-Type', 'application/json');

    // return this.httpClient.post('api',
    //   JSON.stringify({ email, password }),{ headers })
    //   .map(res => res.json())
    //   .map(res => {
    //     localStorage.setItem('auth_token', res.auth_token);
    //     // this.loggedIn = true;
    //     // this._authNavStatusSource.next(true);
    //     return true;
    //   })
    //   .catch(this.handleError);
  }

}