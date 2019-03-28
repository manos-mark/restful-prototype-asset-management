import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, CanActivateChild } from '@angular/router';
import { AuthService } from './auth.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {

    constructor(private authService: AuthService,
                private router: Router) {}

    canActivate(route: ActivatedRouteSnapshot,
                state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        return this.authService.isAuthenticated()
            .then(
                (authenticated: boolean) => {
                    if (authenticated) {
                        return true;
                    } else {
                        this.router.navigate(['/login']);
                        return false;
                    }
                }
            );
    }

    canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.canActivate(route, state);
    }
    // canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    //     if (this.authService.isAuthenticated()) {
    //         return this.authService.isAuthenticated();
    //     } else {
    //         this.router.navigate(['/login'])
    //         return false;
    //     }
    // }

    // canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    //     return this.canActivate(route, state);
    // }
}