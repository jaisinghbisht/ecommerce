import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const REFRESH_TOKEN_KEY = 'auth-refresh-token';
const USER_KEY = 'auth-user';

/**
 * A service for interacting with the browser's storage.
 * This provides a safe, typed wrapper around localStorage.
 */
@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  /**
   * Clears all stored authentication data.
   */
  public clean(): void {
    window.localStorage.clear();
  }

  /**
   * Saves the JWT access token.
   * @param token The token to save.
   */
  public saveToken(token: string): void {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  /**
   * Retrieves the JWT access token.
   */
  public getToken(): string | null {
    return window.localStorage.getItem(TOKEN_KEY);
  }

  /**
   * Saves the refresh token.
   * @param token The refresh token to save.
   */
  public saveRefreshToken(token: string): void {
    window.localStorage.removeItem(REFRESH_TOKEN_KEY);
    window.localStorage.setItem(REFRESH_TOKEN_KEY, token);
  }

  /**
   * Retrieves the refresh token.
   */
  public getRefreshToken(): string | null {
    return window.localStorage.getItem(REFRESH_TOKEN_KEY);
  }

  /**
   * Saves the user's information.
   * @param user The user object to save.
   */
  public saveUser(user: any): void {
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  /**
   * Retrieves the user's information.
   */
  public getUser(): any {
    const user = window.localStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return {};
  }

  /**
   * Checks if a user is currently logged in.
   */
  public isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
