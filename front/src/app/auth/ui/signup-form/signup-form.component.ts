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
  selector: "app-signup-form",
  template: `
    <form #form="ngForm" (ngSubmit)="onSignup()">
      <div class="form-field">
        <label for="firstname">Prénom</label>
        <input
          pInputText
          type="text"
          id="firstname"
          name="firstname"
          [(ngModel)]="firstname"
          required
        />
      </div>

      <div class="form-field">
        <label for="lastname">Nom</label>
        <input
          pInputText
          type="text"
          id="lastname"
          name="lastname"
          [(ngModel)]="lastname"
          required
        />
      </div>

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

      <div class="form-field">
        <label for="confirmPassword">Confirmer le mot de passe</label>
        <p-password
          [(ngModel)]="confirmPassword"
          name="confirmPassword"
          id="confirmPassword"
          [toggleMask]="true"
          required
        />
      </div>

      <div class="flex justify-content-between">
        <p-button type="button" (click)="onCancel()" label="Annuler" severity="help" />
        <p-button type="submit" [disabled]="!form.valid || passwordsDontMatch()" label="S'inscrire" severity="success" />
      </div>
      @if(passwordsDontMatch()){
        <p  class="text-red-500 mt-2">
          Les mots de passe ne correspondent pas.
        </p>
      }

    </form>
  `,
  styleUrls: ["./signup-form.component.scss"],
  standalone: true,
  imports: [
    FormsModule,
    ButtonModule,
    InputTextModule,
    PasswordModule,
  ],
  encapsulation: ViewEncapsulation.None
})
export class SignupFormComponent {
  public firstname: string = "";
  public lastname: string = "";
  public email: string = "";
  public password: string = "";
  public confirmPassword: string = "";

  @Output() cancel = new EventEmitter<void>();
  @Output() signup = new EventEmitter<{ firstname: string; lastname: string; email: string; password: string }>();

  onCancel() {
    this.cancel.emit();
  }

  onSignup() {
    if (!this.passwordsDontMatch()) {
      this.signup.emit({
        firstname: this.firstname,
        lastname: this.lastname,
        email: this.email,
        password: this.password
      });
    }
  }

  passwordsDontMatch(): boolean {
    return this.password !== this.confirmPassword && this.password.length > 0 && this.confirmPassword.length > 0;
  }
}
