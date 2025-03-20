import { createAction, props } from '@ngrx/store';
import {Product} from "../../shared/models/product.model";

export const addToCart = createAction('[Cart] Add Product', props<{ product: Product }>());
export const removeFromCart = createAction('[Cart] Remove Product', props<{ productId: number }>());
export const addToCartSuccess = createAction(
  '[Cart] Add to Cart Success',
  props<{ message: string }>()
);

export const addToCartFailure = createAction(
  '[Cart] Add to Cart Failure',
  props<{ error: string }>()
);

export const removeFromCartSuccess = createAction(
  '[Cart] Remove From Cart Success',
  props<{ productId: number }>()
);

export const removeFromCartFailure = createAction(
  '[Cart] Remove From Cart Failure',
  props<{ error: string }>()
);
