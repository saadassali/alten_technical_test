import {inject, Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {catchError, Observable, tap, throwError} from "rxjs";
import {Product} from "../../shared/models/product.model";

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  private readonly http = inject(HttpClient);
  private readonly path = "http://localhost:8080/api/wishlist";

  public addToWishlist(productId: number): Observable<string> {
    const params = new HttpParams().set("productId", productId.toString());
    return this.http.post<string>(this.path + "/add", null, { params }).pipe(
      catchError((error) => {
        console.error("Error adding product to Wishlist:", error);
        return throwError(() => new Error("Failed to add product to Wishlist"));
      })
    );
  }

  removeFromWishlist(productId: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.path}${productId}`);
  }

  public getProductsInWishlist(): Observable<Product[]> {
    return this.http.get<Product[]>(this.path);
  }
}
