import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { tap, withLatestFrom } from 'rxjs/operators';
import * as CartActions from './cart.actions';
import { selectCartState } from './cart.selectors';

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

  constructor(private actions$: Actions, private store: Store) {}
}
