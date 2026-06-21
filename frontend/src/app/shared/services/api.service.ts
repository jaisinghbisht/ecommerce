import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiConfigurationService } from '../../core/services/api-configuration.service';

/**
 * A generic, base service for making HTTP requests to the backend API.
 * This service should be used by feature-specific services to handle
 * common CRUD operations.
 */
@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(
    private http: HttpClient,
    private apiConfig: ApiConfigurationService
  ) { }

  /**
   * Performs a GET request.
   * @param path The path of the API endpoint.
   * @param params Optional HTTP parameters.
   */
  get<T>(path: string, params: HttpParams = new HttpParams()): Observable<T> {
    return this.http.get<T>(`${this.apiConfig.rootUrl}${path}`, { params });
  }

  /**
   * Performs a PUT request.
   * @param path The path of the API endpoint.
   * @param body The request body.
   */
  put<T>(path: string, body: object = {}): Observable<T> {
    return this.http.put<T>(`${this.apiConfig.rootUrl}${path}`, body);
  }

  /**
   * Performs a POST request.
   * @param path The path of the API endpoint.
   * @param body The request body.
   */
  post<T>(path: string, body: object = {}): Observable<T> {
    return this.http.post<T>(`${this.apiConfig.rootUrl}${path}`, body);
  }

  /**
   * Performs a DELETE request.
   * @param path The path of the API endpoint.
   */
  delete<T>(path: string): Observable<T> {
    return this.http.delete<T>(`${this.apiConfig.rootUrl}${path}`);
  }
}
