import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, CanActivateChild } from '@angular/router';
import { AuthService } from './auth.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { WindowPopService } from '../shared/window-pop/window-pop.service';

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {

    constructor(private authService: AuthService,
                private router: Router,
                private windowPopService: WindowPopService) {}

    canActivate(route: ActivatedRouteSnapshot,
                state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        return this.authService.isAuthenticated()
            .then(
                (authenticated: boolean) => {
                    if (authenticated) {
                        if (!this.authService.getCurrentUser().acceptedCookies) {
                            this.windowPopService.setTitle("Cookies");
                            this.windowPopService.setContext("This site uses cookies");
                            this.windowPopService.setDetails("Do you accept?");
                            this.windowPopService.setCookiesToDB(true);
                            this.windowPopService.activate();
                        }
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