import {
  Component,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import {BadgeModule} from "primeng/badge";
import {Observable} from "rxjs";
import {Store} from "@ngrx/store";
import {selectCartCount} from "./cart/store/cart.selectors";
import {selectWishlistCount} from "./wishlist/store/wishlist.selectors";
import {AsyncPipe, NgIf} from "@angular/common";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, BadgeModule, AsyncPipe, NgIf],
})
export class AppComponent {
  title = "ALTEN SHOP";

  cartCount$: Observable<number>;
  wishlistCount$: Observable<number>;

  constructor(private store: Store) {
    this.cartCount$ = this.store.select(selectCartCount);
    this.wishlistCount$ = this.store.select(selectWishlistCount);
  }
}
