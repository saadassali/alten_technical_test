import { createSelector, createFeatureSelector } from '@ngrx/store';
import { WishlistState } from './wishlist.state';

export const selectWishlistState = createFeatureSelector<WishlistState>('wishlist');
export const selectWishlistItems = createSelector(selectWishlistState, state => state.wishlist);
export const selectWishlistCount = createSelector(selectWishlistItems, wishlist => wishlist.length);
