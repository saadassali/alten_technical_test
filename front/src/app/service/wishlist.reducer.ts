import { createReducer, on } from '@ngrx/store';
import { initialWishlistState, WishlistState } from './wishlist.state';
import * as WishlistActions from './wishlist.actions';

export const wishlistReducer = createReducer(
  initialWishlistState,

  // Add product to wishlist (No duplicates allowed)
  on(WishlistActions.addToWishlist, (state, { product }) => ({
    wishlist: state.wishlist.some(item => item.id === product.id) ? state.wishlist : [...state.wishlist, product]
  })),

  // Remove product from wishlist
  on(WishlistActions.removeFromWishlist, (state, { productId }) => ({
    wishlist: state.wishlist.filter(item => item.id !== productId)
  })),

  // Move product to cart (Remove from wishlist)
  on(WishlistActions.moveToCart, (state, { product }) => ({
    wishlist: state.wishlist.filter(item => item.id !== product.id)
  }))
);
