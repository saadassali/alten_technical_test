import { Component, inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { CommonModule } from '@angular/common';
import {selectWishlistCount, selectWishlistItems} from "../service/wishlist.selectors";
import {Product} from "../products/data-access/product.model";
import * as WishlistActions from '../service/wishlist.actions';
import * as CartActions from '../service/cart.actions';

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [CommonModule],
  template: `
    <h2>Wishlist ({{ wishlistCount$ | async }})</h2>
    <ul>
      <li *ngFor="let product of wishlist$ | async">
        {{ product.name }} - {{ product.price | currency }}
        <button (click)="moveToCart(product)">Move to Cart</button>
        <button (click)="removeFromWishlist(product.id)">Remove</button>
      </li>
    </ul>
  `,
})
export class WishlistComponent {
  private store = inject(Store);
  wishlist$ = this.store.select(selectWishlistItems);
  wishlistCount$ = this.store.select(selectWishlistCount);

  moveToCart(product: Product) {
    // Dispatch move to cart action
    this.store.dispatch(WishlistActions.moveToCart({ product }));
    // Dispatch add to cart action
    this.store.dispatch(CartActions.addToCart({ product }));
  }

  removeFromWishlist(productId: number) {
    this.store.dispatch(WishlistActions.removeFromWishlist({ productId }));
  }
}
