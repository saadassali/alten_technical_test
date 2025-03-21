import { Component, OnInit, inject, signal } from "@angular/core";
import { Product } from "app/shared/models/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import {FormsModule} from "@angular/forms";
import {RatingModule} from "primeng/rating";
import {NgClass} from "@angular/common";
import {SigninFormComponent} from "../ui/signin-form/signin-form.component";
import {AuthService} from "../core/auth.service";



@Component({
  selector: "app-product-list",
  templateUrl: "./signin.component.html",
  styleUrls: ["./signin.component.scss"],
  standalone: true,
  imports: [DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent, FormsModule, RatingModule, NgClass,SigninFormComponent],
})
export class SigninComponent implements OnInit {
  isAuthenticated = false;

  constructor(private authService: AuthService,) {
  }

  ngOnInit(): void {
    this.isAuthenticated = this.authService.isAuthenticated()
    }
  logout() {
    console.log("lougout")
    this.isAuthenticated=false;
    this.authService.logout()

  }


}
