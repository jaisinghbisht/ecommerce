export interface ProductResponse {
  id: number;
  sku: string;
  name: string;
  description: string;
  price: number;
  stock: number;
  createdAt: string; // Assuming ISO 8601 date string
  categoryName: string;
}
