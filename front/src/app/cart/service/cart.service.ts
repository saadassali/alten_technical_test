import {inject, Injectable, signal} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {catchError, Observable, tap, throwError} from "rxjs";
import {Product} from "../../shared/models/product.model";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private readonly http = inject(HttpClient);
  private readonly path = "http://localhost:8080/api/cart";

  public addToCart(productId: number): Observable<string> {
    const params = new HttpParams().set("productId", productId.toString());

    return this.http.post<string>(this.path , null, { params }).pipe(
      catchError((error) => {
        console.error("Error adding product to cart:", error);
        return throwError(() => new Error("Failed to add product to cart"));
      })
    );
  }
  removeFromCart(productId: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.path}/${productId}`);
  }

  public getProductsInCart(): Observable<Product[]> {
    return this.http.get<Product[]>(this.path).pipe(
      catchError((error) => {
        return this.http.get<Product[]>("assets/products.json");
      }),
    );
  }
}
