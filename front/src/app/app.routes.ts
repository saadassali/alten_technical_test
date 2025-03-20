import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import {CartComponent} from "./cart/cart.component";
import {WishlistComponent} from "./wishlist/wishlist.component";

export const APP_ROUTES: Routes = [
  {
    path: "home",
    component: HomeComponent,
  },
  {
    path: "cart",
    component: CartComponent,
  },
  {
    path: "wishlist",
    component: WishlistComponent,
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES)
  },
  {
    path: "auth",
    loadChildren: () =>
      import("./auth/auth.routes").then((m) => m.AUTH_ROUTES)
  },
  { path: "", redirectTo: "home", pathMatch: "full" },
];
