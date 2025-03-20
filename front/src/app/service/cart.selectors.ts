import { createSelector, createFeatureSelector } from '@ngrx/store';
import { CartState } from './cart.state';

export const selectCartState = createFeatureSelector<CartState>('cart');
export const selectCartItems = createSelector(selectCartState, state => state.cart);
export const selectCartCount = createSelector(selectCartItems, cart => cart.length);
