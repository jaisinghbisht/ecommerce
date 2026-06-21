import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable, map } from 'rxjs';
import { AuthService } from '../../features/auth/services/auth.service';

/**
 * A route guard that checks if the logged-in user has the 'ADMIN' role.
 * If the user is not an admin, they are redirected.
 */
@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    return this.authService.currentUser$.pipe(
      map(user => {
        if (user && user.role === 'ROLE_ADMIN') {
          return true;
        } else {
          // Redirect to a 'forbidden' page or home
          return this.router.parseUrl('/');
        }
      })
    );
  }
}
