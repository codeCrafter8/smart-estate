import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
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
  
  constructor(private http: HttpClient) {}

  signUp(authRequest: AuthRequestDto): Observable<AuthResponseDto> {
    return this.http.post<AuthResponseDto>(`${this.apiUrl}/auth/sign-up`, authRequest).pipe(
      tap((response: AuthResponseDto) => {
        localStorage.setItem('token', response.token);
      })
    );
  }

  login(authRequest: AuthRequestDto): Observable<AuthResponseDto> {
    return this.http.post<AuthResponseDto>(`${this.apiUrl}/auth/login`, authRequest).pipe(
      tap((response: AuthResponseDto) => {
        localStorage.setItem('token', response.token);
      })
    );
  }
}
