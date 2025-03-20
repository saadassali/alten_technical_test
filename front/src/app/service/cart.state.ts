import {Product} from "../products/data-access/product.model";

export interface CartState {
  cart: Product[];
}

const savedCart = localStorage.getItem('cart_state');
export const initialCartState: CartState = savedCart ? JSON.parse(savedCart) : { cart: [] };
