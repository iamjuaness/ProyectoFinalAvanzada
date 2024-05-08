import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';


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

  public login(token: string) {
    this.setToken(token);
    this.loggedInSubject.next(true)
    this.router.navigate(["/map"]);
  }

  public logout() {
    window.sessionStorage.clear();
    this.loggedInSubject.next(false)
    this.router.navigate(["/login"]);
  }

  private decodePayload(token: string): any {
    const payload = token!.split(".")[1];
    const payloadDecoded = Buffer.from(payload, 'base64').toString('ascii');
    const values = JSON.parse(payloadDecoded);
    return values;
  }
}
