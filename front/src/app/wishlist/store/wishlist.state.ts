import {Product} from "../../shared/models/product.model";

export interface WishlistState {
  wishlist: Product[];
}

const savedWishlist = localStorage.getItem('wishlist_state');
export const initialWishlistState: WishlistState = savedWishlist ? JSON.parse(savedWishlist) : { wishlist: [] };
