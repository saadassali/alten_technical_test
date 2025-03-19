import {
  Component,
  EventEmitter,
  Output,
  ViewEncapsulation,
} from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ButtonModule } from "primeng/button";
import { InputTextModule } from "primeng/inputtext";
import { PasswordModule } from "primeng/password";

@Component({
  selector: "",
  template: `
    <form #form="ngForm" (ngSubmit)="onSignIn()">
      <div class="form-field">
        <label for="email">Email</label>
        <input
          pInputText
          type="email"
          id="email"
          name="email"
          [(ngModel)]="email"
          required
        />
      </div>

      <div class="form-field">
        <label for="password">Mot de passe</label>
        <p-password
          [(ngModel)]="password"
          name="password"
          id="password"
          [toggleMask]="true"
          required
        />
      </div>

      <div class="flex justify-content-between">
        <p-button type="button" (click)="onCancel()" label="Annuler" severity="help" />
        <p-button type="submit" [disabled]="!form.valid" label="Se connecter" severity="success" />
      </div>
    </form>
  `,
  styleUrls: ["./signin-form.component.scss"],
  standalone: true,
  imports: [
    FormsModule,
    ButtonModule,
    InputTextModule,
    PasswordModule,
  ],
  encapsulation: ViewEncapsulation.None
})
export class SigninFormComponent {
  public email: string = "";
  public password: string = "";

  @Output() cancel = new EventEmitter<void>();
  @Output() signIn = new EventEmitter<{ email: string; password: string }>();

  onCancel() {
    this.cancel.emit();
  }

  onSignIn() {
    this.signIn.emit({ email: this.email, password: this.password });
  }
}
