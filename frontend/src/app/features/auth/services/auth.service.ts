import { Injectable } from '@angular/core';
import { ApiService } from '../../../shared/services/api.service';
import { StorageService } from '../../../core/services/storage.service';
import { LoginRequest } from '../models/login-request.model';
import { AuthenticationResponse } from '../models/authentication-response.model';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

/**
 * Service for handling user authentication (login, logout).
 * Interacts with the backend API and manages tokens via StorageService.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly AUTH_PATH = '/auth'; // Base path for authentication endpoints

  // BehaviorSubject to emit the authentication status
  private _isAuthenticated = new BehaviorSubject<boolean>(false);
  public isAuthenticated$ = this._isAuthenticated.asObservable();

  // BehaviorSubject to emit user information
  private _currentUser = new BehaviorSubject<any | null>(null);
  public currentUser$ = this._currentUser.asObservable();

  constructor(
    private apiService: ApiService,
    private storageService: StorageService,
    private router: Router
  ) {
    // Initialize authentication status and user from storage on service creation
    this._isAuthenticated.next(this.storageService.isLoggedIn());
    if (this.storageService.isLoggedIn()) {
      this._currentUser.next(this.storageService.getUser());
    }
  }

  /**
   * Sends a login request to the backend.
   * On successful login, stores tokens and user info, then navigates to the dashboard.
   * @param credentials User's email and password.
   * @returns An Observable of the AuthenticationResponse.
   */
  login(credentials: LoginRequest): Observable<AuthenticationResponse> {
    return this.apiService.post<AuthenticationResponse>(`${this.AUTH_PATH}/login`, credentials).pipe(
      tap(response => {
        if (response.accessToken && response.refreshToken) {
          this.storageService.saveToken(response.accessToken);
          this.storageService.saveRefreshToken(response.refreshToken);
          // Assuming the backend returns user details like email and role in the auth response
          const userDetails = { email: response.email, role: response.role };
          this.storageService.saveUser(userDetails);

          this._isAuthenticated.next(true);
          this._currentUser.next(userDetails);
          this.router.navigate(['/dashboard']);
        }
      })
    );
  }

  /**
   * Logs out the user by clearing local storage and navigating to the login page.
   */
  logout(): void {
    this.storageService.clean();
    this._isAuthenticated.next(false);
    this._currentUser.next(null);
    this.router.navigate(['/auth/login']);
  }

  /**
   * Retrieves the current access token.
   * @returns The access token or null if not found.
   */
  getAccessToken(): string | null {
    return this.storageService.getToken();
  }

  /**
   * Retrieves the current refresh token.
   * @returns The refresh token or null if not found.
   */
  getRefreshToken(): string | null {
    return this.storageService.getRefreshToken();
  }

  /**
   * Retrieves the current user's role.
   * @returns The user's role string (e.g., 'ROLE_ADMIN', 'ROLE_USER') or null.
   */
  getUserRole(): string | null {
    const user = this._currentUser.getValue();
    return user ? user.role : null;
  }

  /**
   * Retrieves the current user object.
   * @returns The user object or null.
   */
  getCurrentUser(): any | null {
    return this._currentUser.getValue();
  }
}
