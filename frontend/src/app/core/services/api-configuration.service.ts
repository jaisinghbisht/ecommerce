import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

/**
 * Provides the base URL for the backend API.
 * This service reads the URL from the environment files, allowing for
 * easy switching between development and production environments.
 */
@Injectable({
  providedIn: 'root'
})
export class ApiConfigurationService {

  /**
   * The base URL for the API.
   */
  public readonly rootUrl: string = environment.apiUrl;

  constructor() { }
}
