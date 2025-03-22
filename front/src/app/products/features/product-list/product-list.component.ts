import {Component, OnInit, inject, signal, computed} from "@angular/core";
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
import {ProductItemComponent} from "../../../shared/ui/product-item/product-item.component";
import {DropdownModule} from "primeng/dropdown";
import {SelectItem} from "primeng/api";

const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent, FormsModule, RatingModule, NgClass, ProductItemComponent, DropdownModule],
})
export class ProductListComponent implements OnInit {
  // public readonly categories =["","Accessories","Fitness","Clothing","Electronics"]
  public readonly categories: SelectItem[] = [
    { value: "Accessories", label: "Accessories" },
    { value: "Fitness", label: "Fitness" },
    { value: "Clothing", label: "Clothing" },
    { value: "Electronics", label: "Electronics" },
  ];
  private readonly productsService = inject(ProductsService);
  category: string = '';

  public readonly products = this.productsService.products;
  public readonly totalRecords = computed(() => this.products().totalElements);

  public rows = 10;
  public currentPage = 0;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);

  ngOnInit() {
    this.productsService.get().subscribe();
    this.loadProducts();
  }
  public onCategoryChange(event: any) {
    this.loadProducts();
  }
  public onPageChange(event: any) {
    this.currentPage = event.first / event.rows;;
    this.rows = event.rows;
    this.loadProducts();
  }
  private loadProducts() {
    let url = `?page=${this.currentPage}&size=${this.rows}`;
    if (this.category.length) {
      url += `&category=${this.category}`;
    }
    this.productsService.getPaginated(url).subscribe();
  }


  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe(() => {
      this.loadProducts();
    });
  }

  public onSave(product: Product) {
    const action = this.isCreation
      ? this.productsService.create(product)
      : this.productsService.update(product);

    action.subscribe(() => this.loadProducts());
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }
}
