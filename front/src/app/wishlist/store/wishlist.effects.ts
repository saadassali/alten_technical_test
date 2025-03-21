import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import {map, mergeMap, tap, withLatestFrom} from 'rxjs/operators';
import * as WishlistActions from './wishlist.actions';
import { selectWishlistState } from './wishlist.selectors';
import {catchError, of} from "rxjs";
import {WishlistService} from "../service/wishlist.service";

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
  addToCart$ = createEffect(() =>
    this.actions$.pipe(
      ofType(WishlistActions.addToWishlist),
      mergeMap(({ product }) =>
        this.wishlistService.addToWishlist(product.id).pipe(
          map((message) => WishlistActions.addToWishlistSuccess({ message })),
          catchError((error) =>
            of(WishlistActions.addToWishlistFailure({ error: error.message }))
          )
        )
      )
    )
  );

  removeFromWishlist$ = createEffect(() =>
    this.actions$.pipe(
      ofType(WishlistActions.removeFromWishlist),
      mergeMap(({ productId }) =>
        this.wishlistService.removeFromWishlist(productId).pipe(
          map(() => WishlistActions.removeFromWishlistSuccess({ productId })),
          catchError((error) =>
            of(WishlistActions.removeFromWishlistFailure({ error: error.message }))
          )
        )
      )
    )
  );
  loadCart$ = createEffect(() =>
    this.actions$.pipe(
      ofType(WishlistActions.loadWishlist),
      mergeMap(() =>
        this.wishlistService.getProductsInWishlist().pipe(
          map((wishlist) => WishlistActions.loadWishlistSuccess({ wishlist })),
          catchError((error) =>
            of(WishlistActions.loadWishlistFailure({ error: error.message }))
          )
        )
      )
    )
  );
  saveLoadedWishlist$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(WishlistActions.loadWishlistSuccess),
        tap(({ wishlist }) => {
          const state = { wishlist }; // matches WishlistState structure
          localStorage.setItem('wishlist_state', JSON.stringify(state));
        })
      ),
    { dispatch: false }
  );
  clearWishlist$ = createEffect(
    () => this.actions$.pipe(
      ofType(WishlistActions.clearWishlist),
      tap(() => {
        localStorage.removeItem('wishlist_state'); // Or whatever key you used
      })
    ),
    { dispatch: false } // Because it's a side-effect only
  );
  constructor(private actions$: Actions, private store: Store,private wishlistService: WishlistService) {}
}
