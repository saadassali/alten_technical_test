import { createAction, props } from '@ngrx/store';
import {Product} from "../../shared/models/product.model";

export const addToWishlist = createAction('[Wishlist] Add Product', props<{ product: Product }>());
export const removeFromWishlist = createAction('[Wishlist] Remove Product', props<{ productId: number }>());
export const moveToCart = createAction('[Wishlist] Move Product to Cart', props<{ product: Product }>());
export const addToWishlistSuccess = createAction(
  '[Wishlist] Add to Wishlist Success',
  props<{ message: string }>()
);

export const addToWishlistFailure = createAction(
  '[Wishlist] Add to Wishlist Failure',
  props<{ error: string }>()
);

export const removeFromWishlistSuccess = createAction(
  '[Wishlist] Remove From Wishlist Success',
  props<{ productId: number }>()
);

export const removeFromWishlistFailure = createAction(
  '[Wishlist] Remove From Wishlist Failure',
  props<{ error: string }>()
);
export const loadWishlist = createAction('[Wishlist] Load Wishlist');
export const loadWishlistSuccess = createAction(
  '[Wishlist] Load Wishlist Success',
  props<{ wishlist: Product[] }>()
);
export const loadWishlistFailure = createAction(
  '[Wishlist] Load Wishlist Failure',
  props<{ error: string }>()
);
export const clearWishlist = createAction('[Wishlist] Clear Wishlist');

