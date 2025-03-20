import { enableProdMode, importProvidersFrom } from "@angular/core";

import { registerLocaleData } from "@angular/common";
import {
  provideHttpClient, withInterceptors,
  withInterceptorsFromDi,
} from "@angular/common/http";
import localeFr from "@angular/common/locales/fr";
import { BrowserModule, bootstrapApplication } from "@angular/platform-browser";
import { provideAnimations } from "@angular/platform-browser/animations";
import { provideRouter } from "@angular/router";
import { APP_ROUTES } from "app/app.routes";
import { ConfirmationService, MessageService } from "primeng/api";
import { DialogService } from "primeng/dynamicdialog";
import { AppComponent } from "./app/app.component";
import { environment } from "./environments/environment";
import {provideEffects} from "@ngrx/effects";
import {provideState, provideStore} from "@ngrx/store";
import {WishlistEffects} from "./app/wishlist/store/wishlist.effects";
import {CartEffects} from "./app/cart/store/cart.effects";
import {wishlistReducer} from "./app/wishlist/store/wishlist.reducer";
import {cartReducer} from "./app/cart/store/cart.reducer";
import {authInterceptor} from "./app/interceptors/auth.service";

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(BrowserModule),
    provideHttpClient(
      withInterceptorsFromDi(),
      withInterceptors([authInterceptor])
    ),
    provideAnimations(),
    provideRouter(APP_ROUTES),
    provideStore(),
    provideState({ name: 'cart', reducer: cartReducer }),
    provideState({ name: 'wishlist', reducer: wishlistReducer }),
    provideEffects(CartEffects, WishlistEffects),
    ConfirmationService,
    MessageService,
    DialogService,
  ],
}).catch((err) => console.log(err));

registerLocaleData(localeFr, "fr-FR");
