import { createReducer, on } from '@ngrx/store';
import { initialCartState } from './cart.state';
import * as CartActions from './cart.actions';

export const cartReducer = createReducer(
  initialCartState,

  // Add product to cart (No duplicates allowed)
  on(CartActions.addToCart, (state, { product }) => ({
    cart: state.cart.some(item => item.id === product.id) ? state.cart : [...state.cart, product]
  })),

  // Remove product from cart
  on(CartActions.removeFromCart, (state, { productId }) => ({
    cart: state.cart.filter(item => item.id !== productId)
  }))
);
