import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { ProductResponse } from '../../models/product.model';
import { Page } from '../../models/page.model';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-product-list-page',
  templateUrl: './product-list-page.component.html',
  styleUrls: ['./product-list-page.component.scss']
})
export class ProductListPageComponent implements OnInit {
  productsPage$!: Observable<Page<ProductResponse>>;
  currentPage = 1;
  pageSize = 12;
  currentFilters: any = {};
  error: string | null = null;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.error = null;
    this.productsPage$ = this.productService.getProducts(this.currentPage - 1, this.pageSize, this.currentFilters).pipe(
      catchError(err => {
        this.error = 'Failed to load products. Please try again later.';
        console.error(err);
        // Return a valid, empty Page object to satisfy the type checker
        return of({
          content: [],
          pageable: { pageNumber: 0, pageSize: 0, sort: { sorted: false, unsorted: true, empty: true }, offset: 0, paged: true, unpaged: false },
          last: true,
          totalPages: 0,
          totalElements: 0,
          size: 0,
          number: 0,
          sort: { sorted: false, unsorted: true, empty: true },
          first: true,
          numberOfElements: 0,
          empty: true
        } as Page<ProductResponse>);
      })
    );
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadProducts();
  }

  onSearchChange(filters: any): void {
    this.currentFilters = filters;
    this.currentPage = 1; // Reset to first page on new search
    this.loadProducts();
  }
}
