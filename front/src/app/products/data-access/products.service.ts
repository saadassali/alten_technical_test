import { Injectable, inject, signal } from "@angular/core";
import { Product } from "../../shared/models/product.model";
import { HttpClient } from "@angular/common/http";
import { catchError, Observable, of, tap } from "rxjs";
import {PaginatedResponse} from "../../shared/models/paginated.rsponse.model";

@Injectable({
    providedIn: "root"
}) export class ProductsService {

    private readonly http = inject(HttpClient);
    private readonly path = "http://localhost:8080/api/products";

    private readonly _paginated_products = signal<PaginatedResponse<Product>>(
      {content:[],totalElements:0,size:0,totalPages:0,number:0},
      );

    public readonly products = this._paginated_products.asReadonly();

    public get(): Observable<PaginatedResponse<Product>> {
        return this.http.get<PaginatedResponse<Product>>(this.path).pipe(
            tap((products) => this._paginated_products.set(products)),
        );
    }

    public create(product: Product): Observable<boolean> {
        return this.http.post<boolean>(this.path, product).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._paginated_products.update(paginated  => ({
            ...paginated,
                content: [product, ...paginated.content],
                totalElements: paginated.totalElements + 1
            }))),
        );
    }

    public update(product: Product): Observable<boolean> {
        return this.http.patch<boolean>(`${this.path}/${product.id}`, product).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._paginated_products.update(products => {
                return ({
                  ...products,
                  content: products.content.map(p => p.id === product.id ? product : p)
                })
            })),
        );
    }

  public delete(productId: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.path}/${productId}`).pipe(
      catchError(() => of(true)),
      tap(() => this._paginated_products.update(products => ({
        ...products,
        content: products.content.filter(product => product.id !== productId),
        totalElements: products.totalElements - 1
      })))
    );
  }

  getPaginated(url:string) {
    return this.http.get<PaginatedResponse<Product>>(`${this.path}${url}`).pipe(
      tap((products) => this._paginated_products.set(products)),
    );
  }

}
