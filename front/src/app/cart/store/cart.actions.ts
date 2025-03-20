import { createAction, props } from '@ngrx/store';
import {Product} from "../../shared/models/product.model";

export const addToCart = createAction('[Cart] Add Product', props<{ product: Product }>());
export const removeFromCart = createAction('[Cart] Remove Product', props<{ productId: number }>());
