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
import {AuthService} from "../../core/auth.service";
import {NgIf} from "@angular/common";

@Component({
  selector: "app-signin-form",
  template: `
    <form #form="ngForm" (ngSubmit)="onSignIn()" class="form-container">
      <div class="form-field">
        <label for="username">Username</label>
        <input
          pInputText
          type="username"
          id="username"
          name="username"
          [(ngModel)]="username"
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
          [feedback]="false"
          required
        />
      </div>
      <p *ngIf="errorMessage" style="color: red;">{{ errorMessage }}</p>

      <div class="flex justify-content-between">
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
    NgIf,
  ],
  encapsulation: ViewEncapsulation.None
})
export class SigninFormComponent {
  public username: string = "";
  public password: string = "";
  constructor(private authService: AuthService) {}
  errorMessage = '';


  onSignIn() {
    this.authService.login(this.username, this.password).subscribe({
      next: () => {
        console.log('Login successful!');
      },
      error: (err) => {
        this.errorMessage = err.message;
        console.error('Login failed:', err);
      }
    });
  }
}
