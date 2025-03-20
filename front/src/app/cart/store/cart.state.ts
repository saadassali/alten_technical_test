import {Product} from "../../shared/models/product.model";

export interface CartState {
  cart: Product[];
}

const savedCart = localStorage.getItem('cart_state');
export const initialCartState: CartState = savedCart ? JSON.parse(savedCart) : { cart: [] };
