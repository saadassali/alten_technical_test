import { Component, inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { CommonModule } from '@angular/common';
import {selectWishlistCount, selectWishlistItems} from "./store/wishlist.selectors";
import {Product} from "../shared/models/product.model";
import * as WishlistActions from './store/wishlist.actions';
import * as CartActions from '../cart/store/cart.actions';
import {ProductItemComponent} from "../shared/ui/product-item/product-item.component";

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [CommonModule, ProductItemComponent],
  template: `
    <h2>Wishlist ({{ wishlistCount$ | async }})</h2>
    <ul>
      <li *ngFor="let product of wishlist$ | async">

        <app-custom-product-item [product]="product"/>

<!--        {{ product.name }} - {{ product.price | currency }}-->
<!--        <button (click)="moveToCart(product)">Move to Cart</button>-->
<!--        <button (click)="removeFromWishlist(product.id)">Remove</button>-->
      </li>
    </ul>
  `,
})
export class WishlistComponent {
  private store = inject(Store);
  wishlist$ = this.store.select(selectWishlistItems);
  wishlistCount$ = this.store.select(selectWishlistCount);

  moveToCart(product: Product) {
    this.store.dispatch(WishlistActions.moveToCart({ product }));
    this.store.dispatch(CartActions.addToCart({ product }));
  }

  removeFromWishlist(productId: number) {
    this.store.dispatch(WishlistActions.removeFromWishlist({ productId }));
  }
}
