import { Component, inject } from '@angular/core';
import { Store } from '@ngrx/store';
import {selectCartCount, selectCartItems} from "../service/cart.selectors";
import {AsyncPipe, NgForOf} from "@angular/common";

import * as CartActions from '../service/cart.actions';
import {ProductItemComponent} from "../products/features/product-list/product-item/product-item.component";

@Component({
  selector: 'app-cart',
  standalone: true,
  template: `
    <h2>Cart ({{ cartCount$ | async }})</h2>
    <ul>
      <li *ngFor="let product of cart$ | async">

        <app-custom-product-item [product]="product"/>
<!--        {{ product.name }} - {{product.price}}-->
<!--        <button (click)="removeFromCart(product.id)">Remove</button>-->
      </li>
    </ul>
  `,
  imports: [
    AsyncPipe,
    NgForOf,
    ProductItemComponent
  ]
})
export class CartComponent {
  private store = inject(Store);
  cart$ = this.store.select(selectCartItems);
  cartCount$ = this.store.select(selectCartCount);

}
