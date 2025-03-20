import {Product} from "../products/data-access/product.model";

export interface CartWishlistState {
  cart: Product[];
  wishlist: Product[];
}

// Load initial state from localStorage if available to persist across refresh
const savedStateJson = localStorage.getItem('cart_wishlist_state');
const savedState: CartWishlistState = savedStateJson ? JSON.parse(savedStateJson) : null;

export const initialCartWishlistState: CartWishlistState = savedState || {
  cart: [],
  wishlist: []
};
