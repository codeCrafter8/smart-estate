import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';

interface AuthRequestDto {
  email: string;
  password: string;
}

interface AuthResponseDto {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl;
  
  private loggedInSubject = new BehaviorSubject<boolean>(this.hasToken());
  public isLoggedIn$ = this.loggedInSubject.asObservable();
  
  constructor(private http: HttpClient) {}

  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  signUp(authRequest: AuthRequestDto): Observable<AuthResponseDto> {
    return this.http.post<AuthResponseDto>(`${this.apiUrl}/auth/sign-up`, authRequest).pipe(
      tap((response: AuthResponseDto) => {
        localStorage.setItem('token', response.token);
        this.loggedInSubject.next(true);
      })
    );
  }

  login(authRequest: AuthRequestDto): Observable<AuthResponseDto> {
    return this.http.post<AuthResponseDto>(`${this.apiUrl}/auth/login`, authRequest).pipe(
      tap((response: AuthResponseDto) => {
        localStorage.setItem('token', response.token);
        this.loggedInSubject.next(true);
      })
    );
  }

  logOut() {
    localStorage.removeItem('token');
    this.loggedInSubject.next(false);  
  }
}
