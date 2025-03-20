import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { tap, withLatestFrom } from 'rxjs/operators';
import * as WishlistActions from './wishlist.actions';
import { selectWishlistState } from './wishlist.selectors';

@Injectable()
export class WishlistEffects {
  saveWishlist$ = createEffect(
    () => this.actions$.pipe(
      ofType(WishlistActions.addToWishlist, WishlistActions.removeFromWishlist, WishlistActions.moveToCart),
      withLatestFrom(this.store.select(selectWishlistState)),
      tap(([_, wishlistState]) => localStorage.setItem('wishlist_state', JSON.stringify(wishlistState)))
    ),
    { dispatch: false }
  );

  constructor(private actions$: Actions, private store: Store) {}
}
