import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {jwtDecode} from "jwt-decode";
import {Observable, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth/'; // Backend API URL

  constructor(private http: HttpClient,private router: Router) {}

  login(username: string, password: string): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(this.apiUrl + "login", { username, password }).pipe(
      tap(response => {
        if (response.token) {
          localStorage.setItem('accessToken', response.token);
          this.router.navigate(['/products']);
        }
      })
    );
  }
  signup(userData: { username: string; email: string; password: string }): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(this.apiUrl+ "signup", userData).pipe(
      tap(response => {
        if (response.token) {
          localStorage.setItem('accessToken', response.token);
          console.log("response.token: ",response.token)
          this.router.navigate(['/products']);
        }
      })
    );
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('accessToken');

    if (!token) {
      return false;
    }

    try {
      const decodedToken: any = jwtDecode(token);
      const isExpired = decodedToken.exp * 1000 < Date.now(); // Check if expired

      if (isExpired) {
        console.warn('Token expired');
        this.logout(); // Redirect to login
        return false;
      }

      return true;
    } catch (error) {
      console.error('Invalid token:', error);
      this.logout();
      return false;
    }
  }

  logout() {
    localStorage.removeItem('accessToken');
    this.router.navigate(['/auth/signin']);
  }
}
