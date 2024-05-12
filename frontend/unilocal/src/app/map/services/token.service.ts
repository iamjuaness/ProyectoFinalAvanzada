import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { Buffer } from "buffer";


const TOKEN_KEY = "AuthToken";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  private loggedInSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(private router: Router) { }

  isLoggedIn(): Observable<boolean> {
    return this.loggedInSubject.asObservable();
  }

  public setToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return sessionStorage.getItem(TOKEN_KEY);
  }


  public isLogged(): boolean {
    if (this.getToken()) {
     return true;
    }
    return false;
  }

  public login(token: string, id:string) {
    this.setToken(token);
    this.loggedInSubject.next(true)
    this.router.navigate([`/dashboard-user/${id}`]).then(() => {
      window.location.reload();
    });
  }

  public logout() {
    window.sessionStorage.clear();
    this.loggedInSubject.next(false)
    this.router.navigate(["/login"]).then(() => {
      window.location.reload();
    });
  }

  public signup(token: string, id: string) {
    this.setToken(token);
    this.loggedInSubject.next(true)
    this.router.navigate([`/dashboard-user/${{id}}`]).then(() => {
      window.location.reload();
    });
  }

  public decodePayload(token: string | null): any {
    if (!token) return null;

    const parts = token.split('.');
    if (parts.length < 2) {
      console.error('Token JWT no válido');
      return null;
    }

    const payload = parts[1];
    try {
      const payloadDecoded = atob(payload);
      const values = JSON.parse(payloadDecoded);
      return values;
    } catch (error) {
      console.error('Error al decodificar payload:', error);
      return null;
    }
  }

  public getCodigo(): string {
    const token = this.getToken();
    if (token) {
      const values = this.decodePayload(token);
      return values.id;
    }
    return "";
  }

  public getEmail(): string {
    const token = this.getToken();
    if (token) {
      const values = this.decodePayload(token);
      return values.sub;
    }
    return "";
  }

  public getPhoto(): string {
    const token = this.getToken();
    if (token) {
      const values = this.decodePayload(token);
      return values.photo;
    }
    return "";
  }

  public getNombre(): string {
    const token = this.getToken();
    if (token) {
      const values = this.decodePayload(token);
      return values.name;
    }
    return "";
  }

  public getRole(): string {
    const token = this.getToken();
    if (token) {
      const values = this.decodePayload(token);
      return values.role;
    }
    return "";
  }
}
