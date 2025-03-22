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

@Component({
  selector: "app-signup-form",
  template: `
    <form #form="ngForm" (ngSubmit)="onSignup()" class="form-container">
      <div class="form-field">
        <label for="username">Username</label>
        <input
          pInputText
          type="text"
          id="username"
          name="username"
          [(ngModel)]="username"
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
  public username: string = "";
  public lastname: string = "";
  public email: string = "";
  public password: string = "";
  public confirmPassword: string = "";

  @Output() cancel = new EventEmitter<void>();
  @Output() signup = new EventEmitter<{ username: string; lastname: string; email: string; password: string }>();

  onSignup() {
    if (!this.passwordsDontMatch()) {
      this.authService.signup({username:this.username,email:this.email, password: this.password}).subscribe({
        next: () => {
          console.log('Signup successful!');
        },
        error: (err) => {
          this.errorMessage = err.message;
          console.error('Signup failed:', err);
        }
      });
    }
  }

  passwordsDontMatch(): boolean {
    return this.password !== this.confirmPassword && this.password.length > 0 && this.confirmPassword.length > 0;
  }
  constructor(private authService: AuthService) {}
  errorMessage = '';


}
