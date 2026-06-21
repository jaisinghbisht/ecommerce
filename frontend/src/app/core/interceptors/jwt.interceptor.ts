import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { StorageService } from '../services/storage.service';
import { ApiConfigurationService } from '../services/api-configuration.service';

/**
 * Intercepts outgoing HTTP requests and adds the JWT Bearer token to the
 * Authorization header if the user is logged in and the request is to our API.
 */
@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(
    private storageService: StorageService,
    private apiConfig: ApiConfigurationService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.storageService.getToken();
    const isApiUrl = request.url.startsWith(this.apiConfig.rootUrl);

    if (token && isApiUrl) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(request);
  }
}
