import { createAction, props } from '@ngrx/store';
import {Product} from "../products/data-access/product.model";

export const addToWishlist = createAction('[Wishlist] Add Product', props<{ product: Product }>());
export const removeFromWishlist = createAction('[Wishlist] Remove Product', props<{ productId: number }>());
export const moveToCart = createAction('[Wishlist] Move Product to Cart', props<{ product: Product }>());
