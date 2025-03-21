import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import {map, mergeMap, tap, withLatestFrom} from 'rxjs/operators';
import * as CartActions from './cart.actions';
import { selectCartState } from './cart.selectors';
import {catchError, of} from "rxjs";
import {CartService} from "../service/cart.service";

@Injectable()
export class CartEffects {
  saveCart$ = createEffect(
    () => this.actions$.pipe(
      ofType(CartActions.addToCart, CartActions.removeFromCart),
      withLatestFrom(this.store.select(selectCartState)),
      tap(([_, cartState]) => localStorage.setItem('cart_state', JSON.stringify(cartState)))
    ),
    { dispatch: false }
  );

  addToCart$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CartActions.addToCart),
      mergeMap(({ product }) =>
        this.cartService.addToCart(product.id).pipe(
          map((message) => CartActions.addToCartSuccess({ message })),
          catchError((error) =>
            of(CartActions.addToCartFailure({ error: error.message }))
          )
        )
      )
    )
  );
  removeFromCart$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CartActions.removeFromCart),
      mergeMap(({ productId }) =>
        this.cartService.removeFromCart(productId).pipe(
          map(() => CartActions.removeFromCartSuccess({ productId })),
          catchError((error) =>
            of(CartActions.removeFromCartFailure({ error: error.message }))
          )
        )
      )
    )
  );
  loadCart$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CartActions.loadCart),
      mergeMap(() =>
        this.cartService.getProductsInCart().pipe(
          map((cart) => CartActions.loadCartSuccess({ cart })),
          catchError((error) =>
            of(CartActions.loadCartFailure({ error: error.message }))
          )
        )
      )
    )
  );

  constructor(private actions$: Actions, private store: Store,private cartService: CartService) {}
}
