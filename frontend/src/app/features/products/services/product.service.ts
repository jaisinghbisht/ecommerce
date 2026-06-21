import { Injectable } from '@angular/core';
import { ApiService } from '../../../shared/services/api.service';
import { Observable } from 'rxjs';
import { ProductResponse } from '../models/product.model';
import { Page } from '../models/page.model';
import { HttpParams } from '@angular/common/http';

/**
 * Service for fetching product data from the backend.
 */
@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private readonly PRODUCTS_PATH = '/products';

  constructor(private apiService: ApiService) { }

  /**
   * Fetches a paginated list of products, with optional filtering.
   * @param page The page number to retrieve.
   * @param size The number of items per page.
   * @param filters Optional filter criteria (name, category, price).
   * @returns An Observable of a paginated ProductResponse.
   */
  getProducts(page: number, size: number, filters: any = {}): Observable<Page<ProductResponse>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    // Add filter parameters if they exist
    if (filters.name) {
      params = params.set('name', filters.name);
    }
    if (filters.minPrice) {
      params = params.set('minPrice', filters.minPrice);
    }
    if (filters.maxPrice) {
      params = params.set('maxPrice', filters.maxPrice);
    }
    // Add other filters like category as needed

    return this.apiService.get<Page<ProductResponse>>(this.PRODUCTS_PATH, params);
  }

  /**
   * Fetches a single product by its ID.
   * @param id The ID of the product to retrieve.
   * @returns An Observable of the ProductResponse.
   */
  getProductById(id: number): Observable<ProductResponse> {
    return this.apiService.get<ProductResponse>(`${this.PRODUCTS_PATH}/${id}`);
  }
}
