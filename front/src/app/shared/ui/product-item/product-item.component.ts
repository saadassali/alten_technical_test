import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {Product} from "../../models/product.model";
import * as CartActions from "../../../cart/store/cart.actions";
import * as WishlistActions from "../../../wishlist/store/wishlist.actions";
import {NgClass} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {Store} from "@ngrx/store";
import {Button} from "primeng/button";
import {RatingModule} from "primeng/rating";
import {CardModule} from "primeng/card";
import {Router} from "@angular/router";

@Component({
  selector: 'app-custom-product-item',
  standalone: true,
  imports: [
    NgClass,
    FormsModule,
    Button,
    RatingModule,
    CardModule
  ],
  templateUrl: './product-item.component.html',
  styleUrl: './product-item.component.css'
})
export class ProductItemComponent implements OnInit{
  private store = inject(Store);
  isCartPage = false;
  isWishListPage = false;
  @Input() product!: any;
  @Output() onUpdate = new EventEmitter<Product>();
  @Output() onDelete = new EventEmitter<Product>();
  constructor(private router: Router) {}

  ngOnInit() {
    this.isCartPage = this.router.url.includes('/cart');
    this.isWishListPage = this.router.url.includes('/wishlist');
  }
  updateProduct(product: Product) {
    this.onUpdate.emit(product);
  }

  deleteProduct(product: Product) {
    this.onDelete.emit(product);
  }

  public addToCart(product: Product) {
    this.store.dispatch(CartActions.addToCart({ product }));
  }

  public addToWishlist(product: Product) {
    this.store.dispatch(WishlistActions.addToWishlist({ product }));
  }

  removeFromCart(productId: number) {
    this.store.dispatch(CartActions.removeFromCart({ productId }));
  }
  removeFromWishList(productId: number) {
    this.store.dispatch(WishlistActions.removeFromWishlist({ productId }));
  }
}
