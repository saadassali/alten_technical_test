import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import { authGuard } from "./auth/core/auth.guard";
import {ContactComponent} from "./contact/ui/contact.component";

export const APP_ROUTES: Routes = [
  {
    path: "home",
    component: HomeComponent,
    canActivate: [authGuard]
  },
  {
    path: "contact",
    component: ContactComponent,
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES),
    canActivate: [authGuard]
  },
  {
    path: "auth",
    loadChildren: () =>
      import("./auth/auth.routes").then((m) => m.AUTH_ROUTES)
  },
  { path: "", redirectTo: "home", pathMatch: "full" },
];
