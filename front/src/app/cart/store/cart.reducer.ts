import { createReducer, on } from '@ngrx/store';
import { initialCartState } from './cart.state';
import * as CartActions from './cart.actions';

export const cartReducer = createReducer(
  initialCartState,

  on(CartActions.addToCart, (state, { product }) => ({
    cart: state.cart.some(item => item.id === product.id) ? state.cart : [...state.cart, product]
  })),

  on(CartActions.removeFromCart, (state, { productId }) => ({
    cart: state.cart.filter(item => item.id !== productId)
  })),

  on(CartActions.clearCart, (state) => ({
    ...state,
    cart: []
  })),
  on(CartActions.loadCartSuccess, (state, { cart }) => ({
    ...state,
    cart
  }))

);
