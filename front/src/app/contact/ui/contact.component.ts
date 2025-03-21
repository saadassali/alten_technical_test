import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Button, ButtonModule} from "primeng/button";
import {NgIf} from "@angular/common";
import {DialogModule} from "primeng/dialog";
import {ProductFormComponent} from "../../products/ui/product-form/product-form.component";
import {InputTextModule} from "primeng/inputtext";
import {InputNumberModule} from "primeng/inputnumber";
import {InputTextareaModule} from "primeng/inputtextarea";

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [
    FormsModule,
    Button,
    NgIf,
    DialogModule,
    ProductFormComponent,
    FormsModule,
    ButtonModule,
    InputTextModule,
    InputTextareaModule,
  ],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {
  contact = { email: '', message: '' };
  successMessage = '';
  errorMessage = '';
  public isDialogVisible = false;

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  sendContact() {
    if (!this.contact.email || !this.contact.message) {
      this.errorMessage = "Veuillez remplir tous les champs.";
      return;
    }

    if (this.messageTooLong()) {
      this.errorMessage = "Le message ne doit pas dépasser 300 caractères.";
      return;
    }

    this.isDialogVisible = true;
  }

  messageTooLong(): boolean {
    return this.contact.message.length > 300;
  }
}
